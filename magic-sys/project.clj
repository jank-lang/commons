(defproject org.jank-lang.commons/magic-sys "0.1-SNAPSHOT"
  :description "Raw package for magic (ligmagic)."
  :url "https://github.com/jank-lang/commons"
  :license {:name "BSD-2-Clause-Darwin"
            :url "https://github.com/file/file/blob/master/COPYING"}
  :plugins [[org.jank-lang/lein-jank "2026.10-1"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build "2026.10-1"]])
