(ns org.sharpx.parser.main
  (:use [org.sharpx.parser htmlgetter oxford-parser]
        org.sharpx.fs-util
        clojure.data)
  (:gen-class))

(def filenames ["20-500.json" "20-501.json" "20-502.json" "20-503.json" "20-600.json"
                "20-1000.json" "20-1100.json" "20-1099.json" "20-1098.json"])
(def html-dir "f:\\web-dict\\oxford\\htmls")
(def analysis-dir "f:\\web-dict\\oxford\\analysis")

(defn test1 []
  (compare [nil nil] [nil nil])
  (diff {:a "a"} {:a "aa"})
  (prn (take-while true? [true 1 "xx" (not nil) nil false]))
  #_ (compare {:a "a"} {:a "aa"})
  #_ (prn (array-map :z "haha" :a "xxxxx"))
  #_ (prn (merge (array-map :z "haha" :a "xx") (array-map :x "x" :b "b") (array-map :aaa nil)))
  #_ (prn (merge (array-map :z "haha" :a "xx") {:x "x" :b "b"})))

(defn -main [& argv]
  (println "oxford dict parser started!")
  #_ (from-fs html-dir analysis-dir -parse)
  #_ (let [htmls (from-mongo "169.254.244.218" 27017 "oxford" "htmls" -parse)]
       (dorun (map println htmls)))
  #_ (from-fs html-dir "" -parser)
  (test1)
  (doseq [filename filenames
          :let [ret (process-html (toks->path [html-dir filename]) analysis-dir -parse)]
          :while (true? ret)])
  #_ (dorun
       (take-while true?
         (map (fn [filename] (process-html (toks->path [html-dir filename]) analysis-dir -parse))
           filenames))))
