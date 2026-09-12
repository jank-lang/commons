(defproject org.jank-lang.commons/glm-sys "0.1-SNAPSHOT"
  :description "Raw package for glm."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url  "https://github.com/g-truc/glm/blob/master/copying.txt"}
  :plugins [[org.jank-lang/lein-jank "2026.09-7"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build-cmake "2026.09-2"]]
  :verbatim-paths ["lib/glm"])
