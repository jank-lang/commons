(require '[babashka.fs :as fs]
         '[jank.build.cmake :as cmake])

(let [out-dir (:out-dir *input*)
      raylib  (get-in *input* [:inputs "org.jank-lang.commons/raylib-sys"])]
  (cmake/build *input* {:defines {"RAYLIB_SYS_ROOT" raylib}})

  (println (str "jank-build::include-dir=" (fs/path out-dir "include")))
  (println (str "jank-build::link-dir=" (fs/path out-dir "lib")))
  (println (str "jank-build::link-library=" "raygui")))
