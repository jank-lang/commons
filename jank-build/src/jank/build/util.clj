(ns jank.build.util
  (:require [clojure.string :as string]))

;; OS identification functions are adapted from the Apache Commons
;; Lang SystemUtils class.
;;
;; https://github.com/apache/commons-lang/blob/32dc4c501e3acba29d53a466eebd611bb2f093a5/src/main/java/org/apache/commons/lang3/SystemUtils.java

(def os-name (System/getProperty "os.name"))

(defn linux? []
  (-> (string/lower-case os-name)
      (string/starts-with? "linux")))

(defn mac? []
  (-> (string/lower-case os-name)
      (string/starts-with? "mac")))

(defn windows? []
  (-> (string/lower-case os-name)
      (string/starts-with? "windows")))

;; Emit common "jank-build::"-prefixed items.

(defn define
  ([k] (println (str "jank-build::define=" k)))
  ([k v] (println (str "jank-build::define=" k "=" v))))

(defn include-dir
  "Add a jank include-dir directive (a -I flag). If a relative path is given
  then it is assumed to be relative to the build output directory."
  [dir]
  (println (str "jank-build::include-dir=" dir)))

(defn link-dir
  "Add a jank link-dir directive (a -L flag). If a relative path is given then
  it is assumed to be relative to the build output directory."
  [dir]
  (println (str "jank-build::link-dir=" dir)))

(defn link-library [lib]
  (println (str "jank-build::link-library=" lib)))

(defn link-static-library [lib]
  (println (str "jank-build::link-static-library=" lib)))

(defn link-framework [framework]
  (println (str "jank-build::link-framework=" framework)))

(defn rerun-if-changed [path]
  (println (str "jank-build::rerun-if-changed=" path)))

(defn rerun-if-env-changed [k]
  (println (str "jank-build::rerun-if-env-changed=" k)))
