(defproject org.jank-lang.commons/gl-sys "2026.09-2"
  :description "Raw package for OpenGL."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url "https://docs.mesa3d.org/license.html"}
  :plugins [[org.jank-lang/lein-jank "2026.09-4"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build-pkg-config "2026.09-1"]])
