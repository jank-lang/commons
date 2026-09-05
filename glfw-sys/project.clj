(defproject org.jank-lang.commons/glfw-sys "2026.09-4"
  :description "Raw package for glfw3."
  :url "https://github.com/jank-lang/commons"
  :license {:name "zlib/libpng"
            :url "https://github.com/glfw/glfw/blob/master/LICENSE.md"}
  :plugins [[org.jank-lang/lein-jank "2026.09-7"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build-pkg-config "2026.09-3"]])
