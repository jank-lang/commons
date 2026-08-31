(defproject org.jank-lang.commons/sqlite3-sys "2026.08-1"
  :description "Raw package for sqlite3."
  :url "https://github.com/jank-lang/commons"
  :license {:name "Public Domain"
            :url "https://www.sqlite.org/copyright.html"}
  :plugins [[org.jank-lang/lein-jank "2026.07-3"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build-pkg-config "2026.06-1"]])
