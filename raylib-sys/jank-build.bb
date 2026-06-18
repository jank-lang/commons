(require '[babashka.fs :as fs]
         '[jank.build.cmake :as cmake])

(let [src-dir (fs/path (:src-dir *input*) "raylib")
      out-dir (:out-dir *input*)
      input   (assoc *input* :src-dir src-dir)]
  (cmake/build input {:defines {"BUILD_EXAMPLES" false
                                "BUILD_SHARED_LIBS" true}})

  (println (str "jank-build::include-dir=" (fs/path out-dir "include")))
  (println (str "jank-build::link-dir=" (fs/path out-dir "lib64")))
  (println (str "jank-build::link-library=" "raylib")))
