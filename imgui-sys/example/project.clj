(defproject imgui-sys/example "0.1-SNAPSHOT"
  :license {:name "MPL 2.0"
            :url "https://www.mozilla.org/en-US/MPL/2.0/"}
  :dependencies [[org.jank-lang.commons/imgui-glfw-sys "2026.09-3"]
                 [org.jank-lang.commons/imgui-opengl2-sys "2026.09-3"]
                 [org.jank-lang.commons/gl-sys "2026.09-2"]]
  :plugins [[org.jank-lang/lein-jank "2026.09-5"]]
  :middleware [leiningen.jank/middleware]
  :main imgui-sys.example
  :profiles {:base {:jank {:target-dir "target/debug"
                           :optimization-level 0}}
             :release {:jank {:target-dir "target/release"
                              :optimization-level 3}}})
