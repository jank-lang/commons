(require '[babashka.fs :as fs]
         '[jank.build.cmake :as cmake])

(let [out-dir (:out-dir *input*)]
  (cmake/build (update *input* :src-dir #(fs/path % "lib/ftxui")) {})
  
  (println (str "jank-build::include-dir=" (fs/path out-dir "include")))
  (println (str "jank-build::link-dir=" (fs/path out-dir "lib64")))
  (println (str "jank-build::link-library=" "ftxui-component")))
  (println (str "jank-build::link-library=" "ftxui-dom"))
  (println (str "jank-build::link-library=" "ftxui-screen"))
