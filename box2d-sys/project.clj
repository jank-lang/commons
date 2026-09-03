(defproject org.jank-lang.commons/box2d-sys "2026.09-2"
  :description "Raw package for box2d."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url  "https://github.com/erincatto/box2d/blob/main/LICENSE"}
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build-cmake "2026.09-1"]]
  :verbatim-paths ["lib/box2d"])
