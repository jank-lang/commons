(require '[babashka.fs :as fs]
         '[jank.build.cmake :refer [cmake]]
         '[jank.build.util :as util])

(let [src-dir (fs/path (:src-dir *input*) "lib" "box2d")
      input   (assoc *input* :src-dir src-dir)
      debug?  (not (pos? (:optimization-level *input*)))]
  (cmake input {:defines {"CMAKE_INSTALL_LIBDIR" "lib"
                          "BOX2D_SAMPLES"        false
                          "BOX2D_VALIDATE"       false
                          "BOX2D_UNIT_TESTS"     false}})
  (util/include-dir "include")
  (util/link-dir "lib")
  (util/link-library (if debug? "box2dd" "box2d")))
