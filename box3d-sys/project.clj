(defproject org.jank-lang.commons/box3d-sys "2026.09-3"
  :description "Raw package for box3d."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url  "https://github.com/erincatto/box3d/blob/main/LICENSE"}
  :plugins [[org.jank-lang/lein-jank "2026.09-9"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build "0.1-SNAPSHOT"]]
  :verbatim-paths ["lib/box3d"])
