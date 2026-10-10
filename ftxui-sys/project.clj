(defproject org.jank-lang.commons/ftxui-sys "2026.09-3"
  :description "Raw package for FTXUI."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url  "https://github.com/ArthurSonzogni/FTXUI/blob/main/LICENSE"}
  :build-dependencies [[org.jank-lang.commons/jank-build "0.1-SNAPSHOT"]]
  :plugins [[org.jank-lang/lein-jank "2026.09-9"]]
  :middleware [leiningen.jank/middleware]
  :verbatim-paths ["lib/ftxui"])
