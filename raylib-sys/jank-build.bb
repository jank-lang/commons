(require '[babashka.fs :as fs]
         '[jank.build.cmake :as cmake])

(let [src-dir (fs/path (:src-dir *input*) "lib" "raylib")
      out-dir (:out-dir *input*)
      input   (assoc *input* :src-dir src-dir)]
  (cmake/build input {:defines {"BUILD_EXAMPLES" false
                                ; raylib has transient deps which aren't handled by
                                ; static linking.
                                "BUILD_SHARED_LIBS" true}})

  (println (str "jank-build::include-dir=" (fs/path out-dir "include")))
  ; macOS uses lib.
  (println (str "jank-build::link-dir=" (fs/path out-dir "lib")))
  ; Linux uses lib64.
  (println (str "jank-build::link-dir=" (fs/path out-dir "lib64")))
  (println (str "jank-build::link-library=" "raylib")))
