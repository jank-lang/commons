(defproject org.jank-lang.commons/sdl-sys "0.1-SNAPSHOT"
  :description "Raw package for SDL."
  :url "https://github.com/jank-lang/commons"
  :license {:name "zlib"
            :url "https://github.com/libsdl-org/SDL/blob/main/LICENSE.txt"}
  :plugins [[org.jank-lang/lein-jank "2026.09-6"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build-cmake "2026.09-2"]]
  :verbatim-paths ["lib/SDL/LICENSE.txt"
                   "lib/SDL/CMakeLists.txt"
                   "lib/SDL/cmake"
                   "lib/SDL/include"
                   "lib/SDL/src"])
