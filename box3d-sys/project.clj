(defproject org.jank-lang.commons/box3d-sys "2026.08-1"
  :description "Raw package for box3d."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url  "https://github.com/erincatto/box3d/blob/main/LICENSE"}
  :plugins [[org.jank-lang/lein-jank "2026.07-3"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build-cmake "2026.06-6"]]
  :verbatim-paths ["lib/box3d"])
