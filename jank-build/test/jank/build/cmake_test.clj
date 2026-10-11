(ns jank.build.cmake-test
  (:require [clojure.test :refer [deftest is]]
            [clojure.string :as string]
            [babashka.fs :as fs]
            [jank.build.cmake :as cm]))

(def test-project-dir (fs/path "test-data/cmake-project"))

(deftest build-example-project-test
  ;; should not throw
  (cm/cmake {:src-dir   test-project-dir
             :build-dir (fs/create-temp-dir {:prefix "jank-build-cmake-test"})
             :out-dir   (fs/create-temp-dir {:prefix "jank-build-cmake-test"})}
            {:defines {:SOME_DEFINE true}
             :target  "all"}))
