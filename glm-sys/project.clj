(defproject org.jank-lang.commons/glm-sys "2026.09-1"
  :description "Raw package for glm."
  :url "https://github.com/jank-lang/commons"
  :license {:name "MIT"
            :url  "https://github.com/g-truc/glm/blob/master/copying.txt"}
  :plugins [[org.jank-lang/lein-jank "2026.09-9"]]
  :middleware [leiningen.jank/middleware]
  :build-dependencies [[org.jank-lang.commons/jank-build "0.1-SNAPSHOT"]]
  :verbatim-paths ["lib/glm"])
