#!/usr/bin/env bb

(def resume-data (clojure.edn/read-string (slurp "resources/data.edn")))

(spit "raw.txt" "")
(spit "raw.txt" "** IF YOU ARE A HUMAN, PLEASE VISIT LINK FOR FORMATTED RESUME" :append true)
(spit "raw.txt" "https://invocatis.github.io/resume/target/resume.html" :append true)
(spit "raw.txt" (cheshire.core/encode resume-data) :append true)
