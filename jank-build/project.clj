(defproject org.jank-lang.commons/jank-build "0.1-SNAPSHOT"
  :description "Build script helper functions"
  :url "https://github.com/jank-lang/commons"
  :license {:name "MPL 2.0"
            :url  "https://www.mozilla.org/en-US/MPL/2.0/"}
  :source-paths ["src" "test"]
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.12.6"]
                                  [babashka/fs "0.5.34"]
                                  [babashka/process "0.6.25"]]}})
