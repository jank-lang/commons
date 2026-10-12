(require '[babashka.fs :as fs]
         '[jank.build.cmake :refer [cmake]]
         '[jank.build.util :as util])

(cmake (update *input* :src-dir #(fs/path % "lib/ftxui")))

(util/include-dir "include")
(util/link-dir "lib")
(util/link-library "ftxui-component")
(util/link-library "ftxui-dom")
(util/link-library "ftxui-screen")
