(require '[jank.build.cmake :refer [cmake]]
         '[jank.build.util :as util])

(let [raylib  (get-in *input* [:inputs "org.jank-lang.commons/raylib-sys"])]
  (cmake *input* {:defines {"RAYLIB_SYS_ROOT" raylib}})

  (util/include-dir "include")
  (util/link-dir "lib")
  (util/link-library "raygui"))
