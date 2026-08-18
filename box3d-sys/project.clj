(defproject org.jank-lang.commons/box3d-sys "2026.08-1"
  :description "Raw package for box3d."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MPL 2.0"
            :url  "https://www.mozilla.org/en-US/MPL/2.0/"}
  :plugins [[org.jank-lang/lein-jank "2026.07-3"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build-cmake "2026.06-6"]]
  :verbatim-paths ["lib/box3d"])
