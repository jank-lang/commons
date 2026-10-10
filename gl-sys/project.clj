(defproject org.jank-lang.commons/gl-sys "2026.09-4"
  :description "Raw package for OpenGL."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url "https://docs.mesa3d.org/license.html"}
  :plugins [[org.jank-lang/lein-jank "2026.09-9"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build "0.1-SNAPSHOT"]])
