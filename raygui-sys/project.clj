(defproject org.jank-lang.commons/raygui-sys "2026.09-4"
  :description "Raw package for raygui."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MPL 2.0"
            :url  "https://www.mozilla.org/en-US/MPL/2.0/"}
  :plugins [[org.jank-lang/lein-jank "2026.09-5"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build-cmake "2026.09-2"]]
  ;; raygui at this revision calls raylib's TextToFloat, which raylib gained in
  ;; 5.5. raygui carries its own copy, but only behind RAYGUI_STANDALONE, which
  ;; this package does not define. So raylib-sys has to stay on raylib 5.5 or
  ;; newer, and against 5.0 that call is an undefined symbol.
  :dependencies [[org.jank-lang.commons/raylib-sys "2026.09-1"]]
  :verbatim-paths ["lib/raygui/LICENSE"
                   "lib/raygui/src/raygui.h"
                   "CMakeLists.txt"
                   "raygui_impl.c"])
