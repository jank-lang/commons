(defproject org.jank-lang.commons/box2d-sys "2026.09-3"
  :description "Raw package for box2d."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url  "https://github.com/erincatto/box2d/blob/main/LICENSE"}
  :plugins [[org.jank-lang/lein-jank "2026.09-9"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build "0.1-SNAPSHOT"]]
  :verbatim-paths ["lib/box2d"])
