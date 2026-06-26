#!/usr/bin/env bb

;; XXX: I originally generated this with Claude and then tested it and built upon it.

;; This script updates package versions and creates release tags. It then
;; pushes to main, to trigger the publish, which happens in CI.
;;
;; Usage:
;; $ ./bin/release gl-sys glfw-sys

(deps/add-deps '{:deps {rewrite-clj/rewrite-clj {:mvn/version "1.2.55"}}})

(require '[rewrite-clj.zip :as z]
         '[clojure.string :as str]
         '[babashka.process :as proc])

;; ---------------------------------------------------------------------------
;; Date-based version helpers
;; ---------------------------------------------------------------------------

(defn current-year-month
  "Returns the current year-month prefix, e.g. \"2026.06\"."
  []
  (let [now (java.time.LocalDate/now)]
    (format "%d.%02d" (.getYear now) (.getMonthValue now))))

(defn bump-version
  "Given the current version string and today's year-month prefix,
  returns the next version string."
  [current-version year-month]
  (if-let [[_ ym minor] (re-matches #"(\d{4}\.\d{2})-(\d+)" current-version)]
    (if (= ym year-month)
      (str year-month "-" (inc (parse-long minor)))
      (str year-month "-1"))
    ; Unrecognized format.
    (str year-month "-1")))

;; ---------------------------------------------------------------------------
;; rewrite-clj helpers
;; ---------------------------------------------------------------------------

(defn zip-seq
  "Depth-first seq of all zipper locations."
  [zloc]
  (take-while (complement z/end?) (iterate z/next zloc)))

(defn read-project-clj
  "Returns the zipper for project.clj at path."
  [path]
  (z/of-file path {:track-position? false}))

(defn get-project-version
  "Returns [project-symbol version-string] from a project.clj zipper."
  [zloc]
  (let [inner   (z/down zloc)           ; defproject
        sym-loc (z/right inner)         ; project symbol, e.g. org.foo/bar
        ver-loc (z/right sym-loc)]      ; version string
    [(z/sexpr sym-loc) (z/sexpr ver-loc)]))

(defn set-project-version
  "Returns the updated file text with the top-level project version replaced."
  [zloc new-version]
  (-> zloc z/down z/right z/right
      (z/replace new-version)
      z/root-string))

(defn find-keyword-loc
  "Returns the zipper location of the first occurrence of kw, or nil."
  [zloc kw]
  (->> (zip-seq zloc)
       (filter #(= kw (z/sexpr %)))
       first))

(defn update-dep-vec
  "If the dependency vector's artifact matches artifact-sym, replaces its
  version with new-version and returns the updated location; else unchanged."
  [dep-vec-loc artifact-sym new-version]
  (let [art-loc (z/down dep-vec-loc)]
    (if (= (z/sexpr art-loc) artifact-sym)
      (-> art-loc z/right (z/replace new-version) z/up)
      dep-vec-loc)))

(defn patch-deps-key
  "Given text and a keyword like :dependencies, replaces the version of
  artifact-sym in the vector that follows the keyword. Returns updated text."
  [text kw artifact-sym new-version]
  (let [zloc   (z/of-string text)
        kw-loc (find-keyword-loc zloc kw)]
    (if-not kw-loc
      text
      (let [vec-loc (z/right kw-loc)]
        (if-not (= :vector (z/tag vec-loc))
          text
          (loop [child (z/down vec-loc)]
            (if (or (nil? child) (z/end? child))
              (z/root-string zloc)
              (let [updated (if (= :vector (z/tag child))
                              (update-dep-vec child artifact-sym new-version)
                              child)]
                (if (not (identical? updated child))
                  (z/root-string updated)
                  (recur (z/right updated)))))))))))

(defn update-dependency-in-file!
  "Patches :dependencies and :build-dependencies in path, replacing
  artifact with new-version. Returns true if the file changed."
  [path artifact-sym new-version]
  (let [original (slurp path)
        result   (reduce (fn [text kw]
                           (patch-deps-key text kw artifact-sym new-version))
                         original
                         [:dependencies :build-dependencies])]
    (when (not= original result)
      (spit path result)
      true)))

;; ---------------------------------------------------------------------------
;; Project discovery
;; ---------------------------------------------------------------------------

(defn immediate-subdirs
  "Returns all immediate subdirectories of dir (as File objects)."
  [dir]
  (->> (file-seq (io/file dir))
       (filter #(and (.isDirectory %)
                     (= (.getParentFile %) (io/file dir))))))

(defn find-all-project-files
  "Returns a map of {dir-name -> project.clj-path-string} for every
  immediate subdirectory of cwd that contains a project.clj."
  []
  (into {}
        (for [d    (immediate-subdirs ".")
              :let [f (io/file d "project.clj")]
              :when (.exists f)]
          [(.getName d) (.getPath f)])))

(defn read-project-info
  "Returns {:dir :path :sym :version} for a project.clj path."
  [dir path]
  (let [zloc          (read-project-clj path)
        [sym version] (get-project-version zloc)]
    {:dir dir :path path :sym sym :version version}))

(defn find-all-projects []
  (into {}
        (map (fn [[dir path]]
               [dir (read-project-info dir path)])
             (find-all-project-files))))

;; ---------------------------------------------------------------------------
;; Topological sort (depth-first post-order)
;;
;; deps-of :: {project -> #{projects-it-depends-on-within-this-run}}
;;
;; Post-order DFS means a node is emitted only after all of its dependencies
;; have been emitted, giving us the correct publish sequence.
;; ---------------------------------------------------------------------------

(defn topo-sort
  "Returns nodes in dependency-first order given deps-of."
  [nodes deps-of]
  (let [visited (atom #{})
        result  (atom [])]
    (letfn [(visit [node]
              (when-not (contains? @visited node)
                (swap! visited conj node)
                (doseq [dep (get deps-of node #{})]
                  (visit dep))
                (swap! result conj node)))]
      (doseq [node nodes]
        (visit node)))
    @result))

;; ---------------------------------------------------------------------------
;; Git helpers
;; ---------------------------------------------------------------------------

(defn git-stage-project! [project]
  (proc/shell ["git" "add" (str project "/project.clj")]))

(defn git-commit-project! [original-all-projects new-all-projects project]
  (let [original-version (get-in original-all-projects [project :version])
        new-version      (get-in new-all-projects [project :version])]
    (proc/shell ["git" "commit"
                 "-m" (str "Release " project)
                 "-m" (str project " :: " original-version " -> " new-version)])))

(defn git-tag-project! [new-all-projects project]
  (let [version (get-in new-all-projects [project :version])]
    (proc/shell ["git" "tag" (str project "/" version)])))

(defn git-push! []
  (proc/shell ["git" "push" "origin" "main" "--tags"]))

;; ---------------------------------------------------------------------------
;; Release logic
;; ---------------------------------------------------------------------------

(defn do-bump!
  "Bumps the version in project.clj at path. Returns the new version string."
  [path current-version]
  (let [ym          (current-year-month)
        new-version (bump-version current-version ym)
        text        (slurp path)
        zloc        (z/of-string text)
        updated     (set-project-version zloc new-version)]
    (spit path updated)
    new-version))

(defn release!
  "Releases a queue of project directories, propagating dependency bumps.
  updated-projects is an atom :: #{dir}
  deps-of          is an atom :: {dir -> #{dirs-this-dir-depends-on, within run}}"
  [initial-dirs all-projects updated-projects deps-of]
  (loop [queue (vec initial-dirs)]
    (when-not (empty? queue)
      (let [project    (first queue)
            rest-queue (subvec queue 1)]
        (if (contains? @updated-projects project)
          ; Already handled, so we'll skip.
          (recur rest-queue)
          (let [info (get all-projects project)]
            (if-not info
              ; No project.clj found in this dir, so we'll skip.
              (recur rest-queue)
              (let [new-version (do-bump! (:path info) (:version info))
                    _           (swap! updated-projects conj project)
                    _           (println (str "Updated " project
                                              " (" (:version info) " -> " new-version ")"))

                    ; Find all other projects that depend on this one and have
                    ; not yet been bumped. Record the edge (dependent -> dependency)
                    ; so topo-sort can order commits correctly.
                    dependents  (for [[other-dir other-info] all-projects
                                      :when (not= other-dir project)
                                      :when (not (contains? @updated-projects other-dir))
                                      :let  [changed? (update-dependency-in-file!
                                                        (:path other-info)
                                                        (:sym info)
                                                        new-version)]
                                      :when changed?]
                                  (do
                                    (println (str "  Found and updated dependency in " other-dir))
                                    ; other-dir depends on project; record that edge.
                                    (swap! deps-of update other-dir (fnil conj #{}) project)
                                    other-dir))]
                (recur (into rest-queue dependents))))))))))

;; ---------------------------------------------------------------------------
;; Main
;; ---------------------------------------------------------------------------

(defn main [args]
  (let [all-projects     (find-all-projects)
        missing          (remove #(contains? all-projects %) args)
        updated-projects (atom #{})
        deps-of          (atom {})]

    (when-not (empty? missing)
      (throw (ex-info (str "Error: no project.clj found for: " (str/join ", " missing)) {})))

    (release! args all-projects updated-projects deps-of)

    ; Stage all changed files so the user can review the full diff before
    ; we start making commits.
    (doseq [project @updated-projects]
      (git-stage-project! project))
    (println "\nThe changes have been applied and staged.")

    (loop []
      (let [choice (do (print "Continue? (y/n) ")
                       (flush)
                       (read-line))]
        (case choice
          "y" nil
          "n" (System/exit 1)
          (recur))))

    ; Commit and tag one project at a time, in dependency-first order, so
    ; that CI sees tags in a sequence it can safely publish.
    (let [new-all-projects (find-all-projects)
          ordered          (topo-sort @updated-projects @deps-of)]
      (println "\nCommitting in order:" (str/join " -> " ordered))
      (doseq [project ordered]
        (git-stage-project! project)
        (git-commit-project! all-projects new-all-projects project)
        (git-tag-project! new-all-projects project)))

    (git-push!)))

(comment
  (main ["jank-build-cmake"]))

(when (= *file* (System/getProperty "babashka.file"))
  (let [args *command-line-args*]
    (when (= "repl" (first args))
      (require '[babashka.nrepl.server])
      (babashka.nrepl.server/start-server!)
      (deref (promise)))

    (when (empty? args)
      (println "Usage: release.bb <project> [<project> ...]")
      (System/exit 1))

    (main args)))
