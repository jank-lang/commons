(require '[jank.build.cmake :refer [cmake]]
         '[jank.build.util :as util])

(let [raylib (get-in *input* [:inputs "org.jank-lang.commons/raylib-sys"])
      box2d (get-in *input* [:inputs "org.jank-lang.commons/box2d-sys"])]
  (cmake *input* {:defines {"RAYLIB_SYS_ROOT" raylib
                            "BOX2D_SYS_ROOT" box2d}})

  (util/include-dir "include")
  (util/link-dir "lib")
  (util/link-library "box2d_draw"))
