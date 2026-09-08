(defproject org.jank-lang.commons/imtui-sys "0.1-SNAPSHOT"
  :description "Raw package for Dear ImTUI."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url "https://github.com/ggerganov/imtui/blob/master/LICENSE"}
  :build-dependencies [[org.jank-lang.commons/jank-build-cmake "2026.09-2"]]
  :dependencies [[org.jank-lang.commons/ncurses-sys "0.1-SNAPSHOT"]]
  :plugins [[org.jank-lang/lein-jank "2026.09-7"]]
  :middleware [leiningen.jank/middleware]
  :verbatim-paths ["lib/imtui/LICENSE"
                   "lib/imtui/CMakeLists.txt"
                   "lib/imtui/cmake"
                   "lib/imtui/src"
                   "lib/imtui/include"
                   "lib/imtui/third-party/CMakeLists.txt"
                   "lib/imtui/third-party/imgui/imgui/LICENSE.txt"
                   "lib/imtui/third-party/imgui/imgui/imgui.h"
                   "lib/imtui/third-party/imgui/imgui/imconfig.h"
                   "lib/imtui/third-party/imgui/imgui/imgui_internal.h"
                   "lib/imtui/third-party/imgui/imgui/imstb_rectpack.h"
                   "lib/imtui/third-party/imgui/imgui/imstb_truetype.h"
                   "lib/imtui/third-party/imgui/imgui/imstb_textedit.h"
                   "lib/imtui/third-party/imgui/imgui/imgui.cpp"
                   "lib/imtui/third-party/imgui/imgui/imgui_draw.cpp"
                   "lib/imtui/third-party/imgui/imgui/imgui_demo.cpp"
                   "lib/imtui/third-party/imgui/imgui/imgui_widgets.cpp"
                   "lib/imtui/third-party/imgui/imgui/imgui_tables.cpp"])
