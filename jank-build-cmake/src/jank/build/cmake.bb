(ns jank.build.cmake
  (:require [babashka.process :as proc]
            [clojure.string :refer [lower-case]]))

(defn macos? []
  (case (lower-case (System/getProperty "os.name"))
    ("mac os x" "darwin") true
    false))

(defn install-rpath []
  (if (macos?)
    "@loader_path"
    "$ORIGIN"))

(defn default-defines [{:keys [out-dir optimization-level static?]}]
  (let [rpath (install-rpath)]
    (merge {"BUILD_SHARED_LIBS"    (if static? "OFF" "ON")
            "CMAKE_BUILD_TYPE"     (if (pos? optimization-level) "Release" "Debug")
            "CMAKE_INSTALL_PREFIX" out-dir}
           (when (and (not static?) rpath)
             {"CMAKE_INSTALL_RPATH" rpath})
           (when (macos?)
             {"CMAKE_SHARED_LIBRARY_RUNTIME_C_FLAG" "-Wl,-rpath,"}))))

(defn build [{:keys [src-dir build-dir] :as input}
             {:keys [defines target] :or {target "install"}}]
  (let [d-flags (map (fn [[k v]] (str "-D" (name k) "=" v))
                     (merge (default-defines input) defines))]
    (proc/shell (concat ["cmake"] d-flags ["-B" build-dir src-dir]))
    (proc/shell ["cmake" "--build" build-dir "--parallel" "--target" target])))
