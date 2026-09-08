(defproject org.jank-lang.commons/imtui-sys "0.1-SNAPSHOT"
  :description "Raw package for Dear ImTUI."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url "https://github.com/ggerganov/imtui/blob/master/LICENSE"}
  :build-dependencies [[org.jank-lang.commons/jank-build-cmake "2026.09-2"]]
  :dependencies [[org.jank-lang.commons/ncurses-sys "0.1-SNAPSHOT"]]
  :plugins [[org.jank-lang/lein-jank "2026.09-7"]]
  :middleware [leiningen.jank/middleware]
  :verbatim-paths ["lib/imtui" "CMakeLists.txt"])
