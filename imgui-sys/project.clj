(defproject org.jank-lang.commons/imgui-sys "2026.09-1"
  :description "Raw package for Dear ImGUI."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url "https://github.com/ocornut/imgui/blob/master/LICENSE.txt"}
  :build-dependencies [[org.jank-lang.commons/jank-build-cmake "2026.07-1"]]
  :dependencies []
  :plugins [[org.jank-lang/lein-jank "2026.09-1"]]
  :middleware [leiningen.jank/middleware]
  :verbatim-paths ["lib/imgui" "CMakeLists.txt"])
