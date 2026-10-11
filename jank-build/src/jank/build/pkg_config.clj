(ns jank.build.pkg-config
  (:require [clojure.string :as string]
            [babashka.process :as proc]
            [babashka.fs :as fs]
            [jank.build.util :as util]))

(defn parse-prefixed [prefix s]
  (for [entry (string/split s #"\s+")
        :when (string/starts-with? entry prefix)]
    (subs entry (count prefix))))

(defn has-static-lib? [lib link-dirs]
  ; TODO: Windows?
  (let [lib-name (str "lib" lib ".a")]
    (some (fn [link-dir]
            (let [link-dir+lib (fs/path link-dir lib-name)]
              (when (fs/exists? link-dir+lib)
                true)))
          link-dirs)))

(defn brew-pkg-config-path [build-input pkg]
  (when (and (util/mac?) (fs/which "brew"))
    (let [{:keys [exit out]} (proc/shell {:out :string
                                          :err :string
                                          :continue true
                                          ; Disable brew's cache and use the already-authorized
                                          ; working directory rather than /private/tmp.
                                          :extra-env {"HOMEBREW_NO_BOOTSNAP" "1"
                                                      "HOMEBREW_NO_AUTO_UPDATE" "1"
                                                      "HOMEBREW_TEMP" (:out-dir build-input)}}
                                         "brew" "--prefix")
          prefix (string/trim out)]
      (when (and (zero? exit) (not (string/blank? prefix)))
        (str prefix "/opt/" pkg "/lib/pkgconfig")))))

(defn pkg-config-path
  "Rebuild the PKG_CONFIG_PATH string with additional paths prepended. nil
  entries are ignored."
  [& paths]
  (let [sep      (System/getProperty "path.separator")
        from-env (some-> (System/getenv "PKG_CONFIG_PATH")
                         (not-empty)
                         (string/split (re-pattern sep)))
        fresh    (filter some? paths)]
    (string/join sep (concat fresh from-env))))

(defn pkg-config
  "Call the `pkg-config` tool and parse link directories, include directories,
  and link libraries.

  jank-build directives will automatically be output for these values."
  [build-input pc-name]
  ;; TODO: parse preprocessor defines from cflags
  (let [pc-cmd    (cond-> ["pkg-config" pc-name "--libs" "--cflags"]
                    (:static? build-input) (conj "--static"))
        brew-path (brew-pkg-config-path build-input pc-name)
        pc-opts   {:extra-env {"PKG_CONFIG_PATH" (pkg-config-path brew-path)}
                   :out       :string}
        pc-output (->> pc-cmd (apply proc/shell pc-opts) :out)
        link-dirs (parse-prefixed "-L" pc-output)]
    (run! util/link-dir link-dirs)
    (run! util/include-dir (parse-prefixed "-I" pc-output))
    (run! #(if (and (:static? build-input) (has-static-lib? % link-dirs))
             (util/link-static-library %)
             (util/link-library %))
          (parse-prefixed "-l" pc-output))))
