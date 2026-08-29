(require '[babashka.fs :as fs]
         '[jank.build.cmake :as cmake])

;; BUILD_SHARED_LIBS is forced OFF rather than left to the cmake helper, which
;; derives it from :static?. lein-jank hardcodes :static? false, so the helper's
;; default would give a shared library. A static archive is the right default
;; for a package this small, and it keeps raygui's calls into raylib resolving
;; against the one libraylib the consumer already links via raylib-sys.
(let [out-dir (:out-dir *input*)
      ;; NOTE: the key is fully qualified. "raylib-sys" returns nil and the
      ;; CMake configure then fails on the empty RAYLIB_SYS_ROOT check.
      raylib  (get-in *input* [:inputs "org.jank-lang.commons/raylib-sys"])]
  (cmake/build *input* {:defines {"RAYLIB_SYS_ROOT"   raylib
                                  "BUILD_SHARED_LIBS" "OFF"}})

  (println (str "jank-build::include-dir=" (fs/path out-dir "include")))
  (println (str "jank-build::link-dir=" (fs/path out-dir "lib")))
  (println (str "jank-build::link-library=" "raygui")))
