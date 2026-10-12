(defproject org.jank-lang.commons/box2d-sys "2026.10-1"
  :description "Raw package for box2d."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url  "https://github.com/erincatto/box2d/blob/main/LICENSE"}
  :plugins [[org.jank-lang/lein-jank "2026.10-1"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build "2026.10-1"]]
  :verbatim-paths ["lib/box2d"])
