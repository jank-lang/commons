(require '[babashka.fs :as fs]
         '[jank.build.cmake :refer [cmake]]
         '[jank.build.util :as util])

(cmake *input*)

(util/include-dir "include")
(util/link-dir "lib")
(util/link-library "imgui")
