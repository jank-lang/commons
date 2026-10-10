(require '[babashka.fs :as fs]
         '[jank.build.cmake :refer [cmake]]
         '[jank.build.util :as util])

(let [src-dir (fs/path (:src-dir *input*) "lib" "cpp-httplib")
      input   (assoc *input* :src-dir src-dir)]
  (cmake input {:defines {"CMAKE_INSTALL_LIBDIR"          "lib"
                          "HTTPLIB_COMPILE"               true
                          ;; TODO: static build causes "Unsupported x86-64 relocation type R_X86_64_TLSLD"
                          "HTTPLIB_SHARED"                true
                          ;; TODO: zstd cmake config module cannot be loaded in CI on macOS
                          "HTTPLIB_USE_ZSTD_IF_AVAILABLE" (not (util/mac?))}})
  (util/include-dir "include")
  (util/link-dir "lib")
  (util/link-library "cpp-httplib")
  (util/define "CPPHTTPLIB_OPENSSL_SUPPORT" 1))
