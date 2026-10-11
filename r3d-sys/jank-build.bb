(require '[babashka.fs :as fs]
         '[jank.build.cmake :refer [cmake]]
         '[jank.build.util :as util])

(let [raylib-sys (get-in *input* [:inputs "org.jank-lang.commons/raylib-sys"])]
  (cmake *input* {:defines {"R3D_IS_MAIN"            false
                            "R3D_WARNINGS_AS_ERRORS" false
                            ;; use raylib from raylib-sys
                            "R3D_RAYLIB_VENDORED"    false
                            ;; TODO: make assimp its own package one day
                            "R3D_ASSIMP_VENDORED"    true
                            "R3D_SUPPORT_ASSIMP"     true
                            "R3D_BUILD_EXAMPLES"     false
                            "raylib_DIR"            (fs/path raylib-sys "lib/cmake/raylib")}})
  (util/include-dir "include")
  (util/link-dir "lib")
  (util/link-library "r3d")
  (util/link-library "assimp"))
