(defproject org.jank-lang.commons/ftxui-sys "0.1-SNAPSHOT"
  :description "Raw package for FTXUI."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MPL 2.0"
            :url  "https://www.mozilla.org/en-US/MPL/2.0/"}
  :build-dependencies [[org.jank-lang.commons/jank-build-cmake "2026.06-2"]]
  :plugins [[org.jank-lang/lein-jank "2026.06-1"]]
  :middleware [leiningen.jank/middleware]
  :verbatim-paths ["lib/ftxui"])
