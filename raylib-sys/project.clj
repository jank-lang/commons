(defproject org.jank-lang.commons/raylib-sys "2026.09-3"
  :description "Raw package for raylib."
  :url "https://github.com/jank-lang/commons"
  :license {:name "zlib/libpng"
            :url  "https://github.com/raysan5/raylib/blob/master/LICENSE"}
  :plugins [[org.jank-lang/lein-jank "2026.09-9"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build "0.1-SNAPSHOT"]]
  :verbatim-paths ["lib/raylib/LICENSE"
                   "lib/raylib/README.md"
                   "lib/raylib/raylib.pc.in"
                   "lib/raylib/CMakeLists.txt"
                   "lib/raylib/CMakeOptions.txt"
                   "lib/raylib/cmake"
                   "lib/raylib/src"])
