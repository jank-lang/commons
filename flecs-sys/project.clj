(defproject org.jank-lang.commons/flecs-sys "2026.10-1"
  :description "Raw package for flecs."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url  "https://github.com/SanderMertens/flecs/blob/master/LICENSE"}
  :plugins [[org.jank-lang/lein-jank "2026.10-1"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build "2026.10-1"]]
  :verbatim-paths ["lib/flecs/distr/flecs.c"
                   "lib/flecs/distr/flecs.h"
                   "CMakeLists.txt"])
