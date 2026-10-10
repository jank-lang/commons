(require '[babashka.fs :as fs]
         '[jank.build.cmake :refer [cmake]]
         '[jank.build.util :as util])

(let [src-dir (fs/path (:src-dir *input*) "lib" "box3d")
      input   (assoc *input* :src-dir src-dir)
      debug?  (not (pos? (:optimization-level *input*)))]
  (cmake input {:defines {"CMAKE_INSTALL_LIBDIR" "lib"
                          "BOX3D_SAMPLES"        false
                          "BOX3D_UNIT_TESTS"     false}})

  (util/include-dir "include")
  (util/link-dir "lib")
  (util/link-library (if debug? "box3dd" "box3d")))
