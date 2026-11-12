(require '[jank.build.cmake :refer [cmake]]
         '[jank.build.util :as util])

(let [flecs-mode (if (pos? (:optimization-level *input*)) "FLECS_NDEBUG" "FLECS_DEBUG")]
  (cmake *input* {:defines {"FLECS_MODE" flecs-mode}})

  (util/define flecs-mode)
  (util/include-dir "include")
  (util/link-dir "lib")
  (util/link-library "flecs"))
