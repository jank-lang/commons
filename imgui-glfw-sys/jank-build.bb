(require '[babashka.fs :as fs]
         '[jank.build.cmake :as cmake])

(let [out-dir   (:out-dir *input*)
      imgui-sys (get-in *input* [:inputs "org.jank-lang.commons/imgui-sys"])]
  (cmake/build *input* {:defines {"BUILD_SHARED_LIBS" true
                                  "IMGUI_SYS_ROOT"    imgui-sys}})
  (println (str "jank-build::link-dir=" (fs/path out-dir "lib")))
  (println "jank-build::link-library=imgui_glfw"))
