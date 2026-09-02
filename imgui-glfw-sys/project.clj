(defproject org.jank-lang.commons/imgui-glfw-sys "2026.09-1"
  :description "Raw package for Dear ImGUI GLFW platform backend."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url "https://github.com/ocornut/imgui/blob/master/LICENSE.txt"}
  :build-dependencies [[org.jank-lang.commons/jank-build-cmake "testing-SNAPSHOT"]]
  :dependencies [[org.jank-lang.commons/glfw-sys "2026.09-1"]
                 [org.jank-lang.commons/imgui-sys "2026.08-2"]]
  :plugins [[org.jank-lang/lein-jank "2026.09-4"]]
  :middleware [leiningen.jank/middleware]
  :verbatim-paths ["CMakeLists.txt"])
