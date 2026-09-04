(require '[babashka.fs :as fs]
         '[jank.build.cmake :as cmake])

(let [src-dir (fs/path (:src-dir *input*) "lib" "SDL")
      out-dir (:out-dir *input*)
      input   (assoc *input* :src-dir src-dir)]
  (cmake/build input {:defines {"CMAKE_INSTALL_LIBDIR" "lib"
                                "SDL_TESTS" false
                                "SDL_EXAMPLES" false}})

  (println (str "jank-build::include-dir=" (fs/path out-dir "include")))
  (println (str "jank-build::link-dir=" (fs/path out-dir "lib")))
  (println (str "jank-build::link-library=" "SDL3")))
