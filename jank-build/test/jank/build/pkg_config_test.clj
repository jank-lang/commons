(ns jank.build.pkg-config-test
  (:require [clojure.test :refer [deftest is]]
            [clojure.string :as string]
            [jank.build.pkg-config :as pc]))

;; zlib should be on all jank-enabled systems.
(deftest find-zlib-test
  (let [out (with-out-str *out* (pc/pkg-config {} "zlib"))]
    (is (string/includes? out "jank-build::link-dir="))
    (is (string/includes? out "jank-build::include-dir="))
    (is (string/includes? out "jank-build::link-library=z"))))
