(require '[babashka.process :as proc]
         '[babashka.fs :as fs])

(let [{:keys [src-dir out-dir build-dir]} *input*]
  (fs/copy-tree (fs/path src-dir "lib/imgui") (fs/path build-dir "lib/imgui")
                {:replace-existing true})
  (fs/copy (fs/path src-dir "Makefile") (fs/path build-dir)
           {:replace-existing true})

  (proc/sh ["make"] {:dir build-dir})
  (fs/copy-tree (fs/path src-dir "lib/imgui") (fs/path out-dir)
                {:replace-existing true})
  (fs/copy (fs/path build-dir "libimgui.so") (fs/path out-dir)
           {:replace-existing true})

  (println (str "jank-build::include-dir=" out-dir))
  (println (str "jank-build::include-dir=" out-dir "/backends"))
  (println (str "jank-build::link-dir=" out-dir))
  (println "jank-build::link-library=imgui"))
