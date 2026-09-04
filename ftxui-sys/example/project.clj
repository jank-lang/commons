(defproject ftxui-sys/example "0.1-SNAPSHOT"
  :license {:name "MPL 2.0"
            :url "https://www.mozilla.org/en-US/MPL/2.0/"}
  :dependencies [[org.jank-lang.commons/ftxui-sys "2026.09-3"]]
  :plugins [[org.jank-lang/lein-jank "2026.09-7"]]
  :middleware [leiningen.jank/middleware]
  :main ftxui-sys.example
  :profiles {:base {:jank {:target-dir "target/debug"
                           :optimization-level 0}}
             :release {:jank {:target-dir "target/release"
                              :optimization-level 3}}})
