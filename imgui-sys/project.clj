(defproject org.jank-lang.commons/imgui-sys "2026.06-6"
  :description "Raw package for Dear ImGUI."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MPL 2.0"
            :url "https://www.mozilla.org/en-US/MPL/2.0/"}
  :build-dependencies [[org.jank-lang.commons/jank-build-cmake "2026.06-6"]]
  :dependencies []
  :plugins [[org.jank-lang/lein-jank "0.7"]]
  :middleware [leiningen.jank/middleware]
  :verbatim-paths ["lib/imgui" "CMakeLists.txt"])
