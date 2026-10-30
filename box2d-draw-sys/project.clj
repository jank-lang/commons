(defproject org.jank-lang.commons/box2d-draw-sys "2026.10-1"
  :description "Raw package for box2d-draw."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MPL 2.0"
            :url  "https://www.mozilla.org/en-US/MPL/2.0/"}
  :plugins [[org.jank-lang/lein-jank "2026.10-1"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build "2026.10-1"]]
  :dependencies [[org.jank-lang.commons/raylib-sys "2026.10-1"]
                 [org.jank-lang.commons/box2d-sys "2026.10-1"]]
  :verbatim-paths ["CMakeLists.txt"
                   "include"
                   "src"])
