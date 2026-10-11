(defproject org.jank-lang.commons/implot3d-sys "0.1-SNAPSHOT"
  :description "Raw package for implot3d."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url "https://github.com/brenocq/implot3d/blob/main/LICENSE"}
  :build-dependencies [[org.jank-lang.commons/jank-build "2026.10-1"]]
  :dependencies [[org.jank-lang.commons/imgui-sys "2026.09-3"]]
  :plugins [[org.jank-lang/lein-jank "2026.10-1"]]
  :middleware [leiningen.jank/middleware]
  :verbatim-paths ["CMakeLists.txt" "lib/implot3d"])
