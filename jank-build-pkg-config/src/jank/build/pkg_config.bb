(ns jank.build.pkg-config
  (:require [clojure.string :as string]
            [babashka.process :as proc]
            [babashka.fs :as fs]))

(defn parse-prefixed [prefix s]
  (for [entry (string/split s #"\s+")
        :when (string/starts-with? entry prefix)]
    (subs entry (count prefix))))

(defn has-static-lib? [lib link-dirs]
  ; TODO: Windows?
  (let [lib-name (str "lib" lib ".a")]
    (some (fn [link-dir]
            (let [link-dir+lib (str link-dir "/" lib-name)]
              (when (fs/exists? link-dir+lib)
                true)))
          link-dirs)))

(defn pkg-config
  "Call the `pkg-config` tool and parse link directories, include directories,
  and link libraries."
  [build-input pc-name]
  ;; TODO: parse preprocessor defines from cflags
  (let [pc-cmd    (cond-> ["pkg-config" pc-name "--libs" "--cflags"]
                    (:static? build-input) (conj "--static"))
        pc-output (->> pc-cmd (apply proc/shell {:out :string}) :out)
        link-dirs (parse-prefixed "-L" pc-output)]
    (doseq [link-dir link-dirs]
      (println (str "jank-build::link-dir=" link-dir)))
    (doseq [include-dir (parse-prefixed "-I" pc-output)]
      (println (str "jank-build::include-dir=" include-dir)))
    (doseq [library (parse-prefixed "-l" pc-output)]
      (if (has-static-lib? library link-dirs)
        (println (str "jank-build::link-static-library=" library))
        (println (str "jank-build::link-library=" library))))))
