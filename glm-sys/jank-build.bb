(require '[babashka.fs :as fs]
         '[jank.build.cmake :refer [cmake]]
         '[jank.build.util :as util])

(let [src-dir (fs/path (:src-dir *input*) "lib" "glm")
      input   (assoc *input* :src-dir src-dir)]
  (cmake input {:defines {"CMAKE_INSTALL_LIBDIR" "lib"
                          "GLM_ENABLE_CXX_20"    "ON"}})

  (util/include-dir "include")
  (util/define "GLM_ENABLE_EXPERIMENTAL")
  (util/link-dir "lib")
  (util/link-library "glm"))
