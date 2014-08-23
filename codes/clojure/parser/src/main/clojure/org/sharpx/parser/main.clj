(ns org.sharpx.parser.main
  (:use [org.sharpx.parser htmlgetter oxford-parser] clojure.data)
  (:gen-class))


(defn test1 []
  (compare [nil nil] [nil nil])
  (diff {:a "a"} {:a "aa"})
  #_ (compare {:a "a"} {:a "aa"})
  #_ (prn (array-map :z "haha" :a "xxxxx"))
  #_ (prn (merge (array-map :z "haha" :a "xx") (array-map :x "x" :b "b") (array-map :aaa nil)))
  #_ (prn (merge (array-map :z "haha" :a "xx") {:x "x" :b "b"})))

(defn -main [& argv]
  (println "oxford dict parser started!")
  #_ (from-fs "f:\\web-dict\\oxford\\htmls" "" -parse)
  #_ (let [htmls (from-mongo "169.254.244.218" 27017 "oxford" "htmls" -parse)]
       (dorun (map println htmls)))
  #_ (from-fs "f:\\web-dict\\oxford\\htmls" "f:\\web-dict\\oxford\\analysis" -parser)
  (test1)
  (doseq [filename ["20-500.json" "20-501.json" "20-502.json" "20-503.json" "20-600.json"
              "20-1000.json" "20-1100.json" "20-1099.json" "20-1098.json"]]
    (process-html (str "f:\\web-dict\\oxford\\htmls\\" filename) "f:\\web-dict\\oxford\\analysis" -parse)))
