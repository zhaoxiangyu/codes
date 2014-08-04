(ns org.sharpx.parser.oxford-test
  (:use org.sharpx.parser.oxford)
  (:use org.sharpx.log)
  (:use [clojure test])
  )

(deftest test-parser
  (let [xml (-parse {:type "e" :html "<html><head><title>test</title></head><body><div id='entryContent'>xxx</div></html>"})]
    (prn xml)
    (is (not (nil? xml)))))

(deftest test-parser2
  (let [xml (-parse {:type "e" :html "<html><head><title>test</title></head><body><div id='entryContent'><div></div>x</ihtml>"})]
    (prn xml)
    (is (not (nil? xml)))))
