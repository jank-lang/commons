(require '[babashka.fs :as fs]
         '[jank.build.cmake :refer [cmake]]
         '[jank.build.util :as util])

(let [src-dir (fs/path (:src-dir *input*) "lib" "raylib")
      input   (assoc *input*
                     :src-dir src-dir
                     ; raylib has transient deps which aren't handled by
                     ; static linking.
                     :static? false)]
  (cmake input {:defines {"BUILD_EXAMPLES" false}})

  (util/include-dir "include")
  (util/link-dir "lib")
  (util/link-library "raylib"))
