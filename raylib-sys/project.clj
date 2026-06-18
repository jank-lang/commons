(defproject org.jank-lang.commons/raylib-sys "2026.06-1"
  :license {:name "MPL 2.0"
            :url  "https://www.mozilla.org/en-US/MPL/2.0/"}
  :plugins [[org.jank-lang/lein-jank "0.7"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.clojars.kylc/jank-build-cmake "2026.06-1"]]
  :verbatim-paths ["raylib"])
