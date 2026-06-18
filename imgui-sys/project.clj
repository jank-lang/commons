(defproject org.jank-lang.commons/imgui-sys "2026.06-1"
  :license {:name "MPL 2.0"
            :url "https://www.mozilla.org/en-US/MPL/2.0/"}
  :dependencies []
  :plugins [[org.jank-lang/lein-jank "0.7"]]
  :middleware [leiningen.jank/middleware]
  :verbatim-paths ["lib/imgui" "Makefile"])
