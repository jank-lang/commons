(defproject org.jank-lang.commons/raylib-sys "2026.08-1"
  :description "Raw package for raylib."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MPL 2.0"
            :url  "https://www.mozilla.org/en-US/MPL/2.0/"}
  :plugins [[org.jank-lang/lein-jank "2026.07-3"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build-cmake "2026.06-6"]]
  :verbatim-paths ["lib/raylib/LICENSE"
                   "lib/raylib/README.md"
                   "lib/raylib/raylib.pc.in"
                   "lib/raylib/CMakeLists.txt"
                   "lib/raylib/CMakeOptions.txt"
                   "lib/raylib/cmake"
                   "lib/raylib/src"])
