(defproject org.jank-lang.commons/r3d-sys "0.1-SNAPSHOT"
  :description "Raw package for r3d."
  :url "https://github.com/jank-lang/commons"
  :license {:name "zlib"
            :url "https://github.com/Bigfoot71/r3d/blob/master/LICENSE"}
  :plugins [[org.jank-lang/lein-jank "2026.10-2"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build "2026.10-1"]]
  :dependencies [[org.jank-lang.commons/raylib-sys "2026.10-1"]]
  :verbatim-paths ["CMakeLists.txt"
                   "lib/r3d/assets"
                   "lib/r3d/cmake"
                   "lib/r3d/external/glad"
                   "lib/r3d/external/uthash"
                   "lib/r3d/external/tinycthread"
                   "lib/r3d/external/assimp/LICENSE"
                   "lib/r3d/external/assimp/CMakeLists.txt"
                   "lib/r3d/external/assimp/assimp.pc.in"
                   "lib/r3d/external/assimp/cmake-modules"
                   "lib/r3d/external/assimp/include"
                   "lib/r3d/external/assimp/code"
                   "lib/r3d/external/assimp/contrib"
                   "lib/r3d/include"
                   "lib/r3d/scripts"
                   "lib/r3d/shaders"
                   "lib/r3d/src"
                   "lib/r3d/CMakeLists.txt"
                   "lib/r3d/LICENSE"])
