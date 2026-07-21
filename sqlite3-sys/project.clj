(defproject org.jank-lang.commons/sqlite3-sys "2026.07-1"
  :description "Raw package for sqlite3."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MPL 2.0"
            :url "https://www.mozilla.org/en-US/MPL/2.0/"}
  :plugins [[org.jank-lang/lein-jank "2026.07-1"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build-pkg-config "2026.06-1"]])
