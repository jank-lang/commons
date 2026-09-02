(defproject raylib-sys/example "0.1-SNAPSHOT"
  :license {:name "MPL 2.0"
            :url "https://www.mozilla.org/en-US/MPL/2.0/"}
  :dependencies [[org.jank-lang.commons/raylib-sys "2026.09-1"]]
  :plugins [[org.jank-lang/lein-jank "2026.09-4"]]
  :middleware [leiningen.jank/middleware]
  :main raylib-sys.example
  :profiles {:base {:jank {:target-dir "target/debug"
                           :optimization-level 0}}
             :release {:jank {:target-dir "target/release"
                              :optimization-level 3}}})
