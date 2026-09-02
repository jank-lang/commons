(require '[clojure.string :refer [lower-case]])
(require '[jank.build.pkg-config :refer [pkg-config]])

; macOS doesn't package OpenGL with pkg-config. It's just globally available.
(if (contains? #{"mac os x" "darwin"} (lower-case (System/getProperty "os.name")))
  (do
    ; However, if macOS has deprecated OpenGL and surfaces warnings about this unless
    ; we provide this define.
    (println "jank-build::define=GL_SILENCE_DEPRECATION")
    (println "jank-build::link-framework=OpenGL"))
  (pkg-config "gl"))
