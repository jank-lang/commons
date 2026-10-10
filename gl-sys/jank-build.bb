(require '[jank.build.pkg-config :refer [pkg-config]]
         '[jank.build.util :as util])

; macOS doesn't package OpenGL with pkg-config. It's just globally available.
(if (util/mac?)
  (do
    ; However, if macOS has deprecated OpenGL and surfaces warnings about this unless
    ; we provide this define.
    (println "jank-build::define=GL_SILENCE_DEPRECATION")
    (println "jank-build::link-framework=OpenGL"))
  (pkg-config *input* "gl"))
