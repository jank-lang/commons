(defproject org.jank-lang.commons/ncurses-sys "2026.09-1"
  :description "Raw package for ncurses."
  :url "https://github.com/jank-lang/commons"
  :license {:name "X11"
            :url "https://invisible-island.net/ncurses/ncurses-license.html"}
  :plugins [[org.jank-lang/lein-jank "2026.09-7"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build-pkg-config "2026.09-3"]])
