(require '[babashka.fs :as fs]
         '[jank.build.cmake :as cmake])

(let [out-dir (:out-dir *input*)
      build-input (assoc *input* :src-dir (fs/path (:src-dir *input*) "lib" "imtui"))]
  (cmake/build build-input {:defines {"CMAKE_INSTALL_LIBDIR" "lib"
                                      "IMTUI_BUILD_EXAMPLES" "OFF"
                                      "IMTUI_SUPPORT_CURL" "OFF"}})

  (println (str "jank-build::include-dir=" (fs/path out-dir "include")))
  (println (str "jank-build::include-dir=" (fs/path out-dir "include/imgui-for-imtui")))
  (println (str "jank-build::link-dir=" (fs/path out-dir "lib")))
  (println (str "jank-build::link-library=" "imtui"))
  (println (str "jank-build::link-library=" "imtui-ncurses"))
  (println (str "jank-build::link-library=" "imgui-for-imtui")))
