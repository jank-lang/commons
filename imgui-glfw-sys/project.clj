(defproject org.jank-lang.commons/imgui-glfw-sys "2026.06-5"
  :description "Raw package for Dear ImGUI GLFW platform backend."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MPL 2.0"
            :url "https://www.mozilla.org/en-US/MPL/2.0/"}
  :build-dependencies [[org.jank-lang.commons/jank-build-cmake "2026.06-5"]]
  :dependencies [[org.jank-lang.commons/glfw-sys "2026.06-1"]
                 [org.jank-lang.commons/imgui-sys "2026.06-5"]]
  :plugins [[org.jank-lang/lein-jank "0.7"]]
  :middleware [leiningen.jank/middleware]
  :verbatim-paths ["CMakeLists.txt"])
