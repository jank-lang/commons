(defproject org.jank-lang.commons/sdl-sys "2026.09-1"
  :description "Raw package for SDL."
  :url "https://github.com/jank-lang/commons"
  :license {:name "zlib"
            :url "https://github.com/libsdl-org/SDL/blob/main/LICENSE.txt"}
  :plugins [[org.jank-lang/lein-jank "2026.10-1"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build "2026.10-1"]]
  :verbatim-paths ["lib/SDL/LICENSE.txt"
                   "lib/SDL/CMakeLists.txt"
                   "lib/SDL/cmake"
                   "lib/SDL/include"
                   "lib/SDL/src"])
