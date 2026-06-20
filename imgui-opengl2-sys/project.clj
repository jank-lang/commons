(defproject org.jank-lang.commons/imgui-opengl2-sys "TODO"
  :description "Raw package for Dear ImGUI OpenGL2 renderer backend."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MPL 2.0"
            :url "https://www.mozilla.org/en-US/MPL/2.0/"}
  :build-dependencies [[org.jank-lang.commons/jank-build-cmake "2026.06-1"]]
  :dependencies [[org.jank-lang.commons/gl-sys "2026.06-1"]
                 [org.jank-lang.commons/imgui-sys "2026.06-1"]]
  :plugins [[org.jank-lang/lein-jank "0.7"]]
  :middleware [leiningen.jank/middleware]
  :verbatim-paths ["CMakeLists.txt"])
