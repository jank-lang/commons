(ns jank.build.cmake
  (:require [babashka.process :as proc]
            [clojure.string :refer [lower-case]]))

(defn install-rpath []
  (when (= "linux" (lower-case (System/getProperty "os.name")))
    "$ORIGIN"))

(defn default-defines [{:keys [out-dir optimization-level static?]}]
  (let [rpath (install-rpath)]
    (merge {"BUILD_SHARED_LIBS"    (if static? "OFF" "ON")
            "CMAKE_BUILD_TYPE"     (if (pos? optimization-level) "Release" "Debug")
            "CMAKE_INSTALL_PREFIX" out-dir}
           (when (and (not static?) rpath)
             {"CMAKE_INSTALL_RPATH" rpath}))))

(defn build [{:keys [src-dir build-dir] :as input}
             {:keys [defines target] :or {target "install"}}]
  (let [d-flags (map (fn [[k v]] (str "-D" (name k) "=" v))
                     (merge (default-defines input) defines))]
    (prn :d-flags d-flags)
    (proc/shell (concat ["cmake"] d-flags ["-B" build-dir src-dir]))
    (proc/shell ["cmake" "--build" build-dir "--parallel" "--target" target])))
