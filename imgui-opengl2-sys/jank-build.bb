(require '[jank.build.cmake :refer [cmake]]
         '[jank.build.util :as util])

(let [imgui-sys (get-in *input* [:inputs "org.jank-lang.commons/imgui-sys"])]
  (cmake *input* {:defines {"IMGUI_SYS_ROOT" imgui-sys}})
  (util/include-dir "include/backends")
  (util/link-dir "lib")
  (util/link-library "imgui_opengl2"))
