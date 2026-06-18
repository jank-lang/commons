(ns jank.build.pkg-config
  (:require [clojure.string :as string]
            [babashka.process :as proc]))

(defn- parse-prefixed [prefix s]
  (for [entry (string/split s #"\s+")
        :when (string/starts-with? entry prefix)]
    (subs entry (count prefix))))

(defn pkg-config
  "Call the `pkg-config` tool and parse link directories, include directories,
  and link libraries."
  [pc-name]
  ;; TODO: parse preprocessor defines from cflags
  (let [pc-cmd    ["pkg-config" pc-name "--libs" "--cflags"]
        pc-output (->> pc-cmd (apply proc/shell {:out :string}) :out)]
    (doseq [link-dir (parse-prefixed "-L" pc-output)]
      (println (str "jank-build::link-dir=" link-dir)))
    (doseq [include-dir (parse-prefixed "-I" pc-output)]
      (println (str "jank-build::include-dir=" include-dir)))
    (doseq [library (parse-prefixed "-l" pc-output)]
      (println (str "jank-build::link-library=" library)))))
