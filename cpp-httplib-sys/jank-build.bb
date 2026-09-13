(require '[babashka.fs :as fs]
         '[jank.build.cmake :as cmake])

(let [src-dir (fs/path (:src-dir *input*) "lib" "cpp-httplib")
      out-dir (:out-dir *input*)
      input   (assoc *input* :src-dir src-dir)]
  (cmake/build input {:defines {"CMAKE_INSTALL_LIBDIR" "lib"
                                "CMAKE_OSX_DEPLOYMENT_TARGET" "10.15"
                                "HTTPLIB_COMPILE"      true
                                ;; TODO: static build causes "Unsupported x86-64 relocation type R_X86_64_TLSLD"
                                "HTTPLIB_SHARED"       true}})
  (println (str "jank-build::include-dir=" (fs/path out-dir "include")))
  (println (str "jank-build::link-dir=" (fs/path out-dir "lib")))
  (println (str "jank-build::link-library=" "cpp-httplib"))
  (println "jank-build::define=CPPHTTPLIB_OPENSSL_SUPPORT=1"))
