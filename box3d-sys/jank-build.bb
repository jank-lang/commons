(require '[babashka.fs :as fs]
         '[jank.build.cmake :as cmake])

(let [src-dir (fs/path (:src-dir *input*) "lib" "box3d")
      out-dir (:out-dir *input*)
      input   (assoc *input* :src-dir src-dir)
      debug?  (not (pos? (:optimization-level *input*)))]
  (cmake/build input {:defines {"CMAKE_INSTALL_LIBDIR" "lib"
                                "BOX3D_SAMPLES" false
                                "BOX3D_UNIT_TESTS" false}})

  (println (str "jank-build::include-dir=" (fs/path out-dir "include")))
  (println (str "jank-build::link-dir=" (fs/path out-dir "lib")))
  (println (str "jank-build::link-library=" (if debug? "box3dd" "box3d"))))
