(require '[babashka.fs :as fs]
         '[jank.build.cmake :as cmake])

(let [out-dir (:out-dir *input*)]
  (cmake/build *input* {:defines {"BUILD_SHARED_LIBS" true}})

  (println (str "jank-build::include-dir=" (fs/path out-dir "include")))
  (println (str "jank-build::link-dir=" (fs/path out-dir "lib")))
  (println (str "jank-build::link-library=" "imgui")))
