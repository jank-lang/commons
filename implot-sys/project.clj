(defproject org.jank-lang.commons/implot-sys "2026.10-1"
  :description "Raw package for implot."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url "https://github.com/epezent/implot/blob/master/LICENSE"}
  :build-dependencies [[org.jank-lang.commons/jank-build "2026.10-1"]]
  :dependencies [[org.jank-lang.commons/imgui-sys "2026.10-1"]]
  :plugins [[org.jank-lang/lein-jank "2026.10-1"]]
  :middleware [leiningen.jank/middleware]
  :verbatim-paths ["CMakeLists.txt" "lib/implot"])
