(require '[babashka.fs :as fs]
         '[jank.build.cmake :refer [cmake]]
         '[jank.build.util :as util])

(let [src-dir (str (fs/path (:src-dir *input*) "lib" "SDL"))
      input   (assoc *input* :src-dir src-dir)]
  (cmake input {:defines {"CMAKE_INSTALL_LIBDIR"        "lib"
                          ;; per https://wiki.libsdl.org/SDL3/README-macos
                          "CMAKE_OSX_DEPLOYMENT_TARGET" "10.13"
                          "SDL_WAYLAND"                 false
                          "SDL_TESTS"                   false
                          "SDL_EXAMPLES"                false}})

  (util/include-dir "include")
  (util/link-dir "lib")
  (util/link-library "SDL3")

  (when (util/mac?)
    (util/link-framework "CoreMedia")
    (util/link-framework "CoreVideo")
    (util/link-framework "Cocoa")
    (util/link-framework "IOKit")
    (util/link-framework "ForceFeedback")
    (util/link-framework "Carbon")
    (util/link-framework "CoreAudio")
    (util/link-framework "AudioToolbox")
    (util/link-framework "AVFoundation")
    (util/link-framework "CoreBluetooth")
    (util/link-framework "CoreGraphics")
    (util/link-framework "CoreMotion")
    (util/link-framework "Foundation")
    (util/link-framework "GameController")
    (util/link-framework "Metal")
    (util/link-framework "CoreHaptics")
    (util/link-framework "QuartzCore")
    (util/link-framework "UniformTypeIdentifiers")))
