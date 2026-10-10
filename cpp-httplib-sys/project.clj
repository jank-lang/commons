(defproject org.jank-lang.commons/cpp-httplib-sys "2026.09-1"
  :description "Raw package for cpp-httplib"
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url "https://github.com/yhirose/cpp-httplib/blob/master/LICENSE"}
  :plugins [[org.jank-lang/lein-jank "2026.09-9"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build "0.1-SNAPSHOT"]]
  :verbatim-paths ["lib/cpp-httplib/README.md"
                   "lib/cpp-httplib/LICENSE"
                   "lib/cpp-httplib/cmake"
                   "lib/cpp-httplib/CMakeLists.txt"
                   "lib/cpp-httplib/httplib.h"
                   "lib/cpp-httplib/split.py"])
