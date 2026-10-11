(ns jank.build.util-test
  (:require [clojure.test :refer [deftest is]]
            [jank.build.util :as util]))

(deftest os-detection-test
  (with-redefs [util/os-name "Linux"]
    (is (util/linux?))
    (is (not (util/mac?)))
    (is (not (util/windows?))))
  (with-redefs [util/os-name "Mac OS X"]
    (is (util/mac?))
    (is (not (util/linux?)))
    (is (not (util/windows?))))
  (with-redefs [util/os-name "Windows XP"]
    (is (util/windows?))
    (is (not (util/linux?)))
    (is (not (util/mac?)))))

(deftest build-directives-test
  (is (= (with-out-str (util/define "KEY"))
         "jank-build::define=KEY\n"))
  (is (= (with-out-str (util/define "KEY" "VAL"))
         "jank-build::define=KEY=VAL\n"))
  (is (= (with-out-str (util/include-dir "/usr/include"))
         "jank-build::include-dir=/usr/include\n"))
  (is (= (with-out-str (util/link-dir "/usr/lib64"))
         "jank-build::link-dir=/usr/lib64\n"))
  (is (= (with-out-str (util/link-library "m"))
         "jank-build::link-library=m\n"))
  (is (= (with-out-str (util/link-framework "Cocoa"))
         "jank-build::link-framework=Cocoa\n"))
  (is (= (with-out-str (util/rerun-if-changed "/home"))
         "jank-build::rerun-if-changed=/home\n"))
  (is (= (with-out-str (util/rerun-if-env-changed "PATH"))
         "jank-build::rerun-if-env-changed=PATH\n")))
