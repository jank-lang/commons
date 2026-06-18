(require '[clojure.string :as string]
         '[babashka.process :as proc])

(defn pkg-config [pc-name var]
  (-> (proc/sh ["pkg-config" pc-name "--variable" var])
      proc/check
      :out
      string/trim-newline))

(let [pc-name "glfw3"]
  (println (str "jank-build::include-dir=" (pkg-config pc-name "includedir")))
  (println (str "jank-build::link-dir=" (pkg-config pc-name "libdir")))
  (println "jank-build::link-library=glfw"))
