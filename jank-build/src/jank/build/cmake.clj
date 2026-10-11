(ns jank.build.cmake
  (:require [babashka.process :as proc]
            [jank.build.util :as util]))

(defn install-rpath []
  (when (util/linux?) "$ORIGIN"))

(defn default-defines [{:keys [out-dir optimization-level static?]
                        :or   {optimization-level 0}}]
  (let [rpath (install-rpath)]
    (merge {"BUILD_SHARED_LIBS"        (if static? "OFF" "ON")
            "CMAKE_BUILD_TYPE"         (if (pos? optimization-level) "Release" "Debug")
            "CMAKE_INSTALL_PREFIX"     out-dir
            ;; Make sure these paths are standardized, otherwise libs may
            ;; sometimes go into lib64/ instea of lib/, etc.
            "CMAKE_INSTALL_BINDIR"     "bin"
            "CMAKE_INSTALL_LIBDIR"     "lib"
            "CMAKE_INSTALL_INCLUDEDIR" "include"}
           (when (and (not static?) rpath)
             {"CMAKE_INSTALL_RPATH" rpath}))))

(defn cmake
  "Run the `cmake` tool in the src-dir, building to the intermediate build-dir,
  and installing artifacts into the out-dir.

  No jank-build directives are output, so make sure to specify the correct
  link/include directories, link libraries, etc. yourself."
  [{:keys [src-dir build-dir] :as input} & {:keys [defines target] :or {target "install"}}]
  (let [d-flags (map (fn [[k v]] (str "-D" (name k) "=" v))
                     (merge (default-defines input) defines))]
    (proc/shell (concat ["cmake"] d-flags ["-B" build-dir src-dir]))
    (proc/shell ["cmake" "--build" build-dir "--parallel" "--target" target])))
