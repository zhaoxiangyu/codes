(ns org.sharpx.parser.main
  (:use [org.sharpx.parser htmlgetter oxford-parser])
  (:gen-class))

(defn -main [& argv]
  (println "oxford dict parser started!")
  #_ (from-fs "f:\\web-dict\\oxford\\htmls" "" -parse)
  #_ (let [htmls (from-mongo "169.254.244.218" 27017 "oxford" "htmls" -parse)]
       (dorun (map println htmls)))
  #_ (from-fs "f:\\web-dict\\oxford\\htmls" "f:\\web-dict\\oxford\\analysis" -parser)
  (doseq [filename ["20-500.json" "20-501.json" "20-502.json" "20-503.json" "20-600.json"
              "20-1000.json" "20-1100.json" "20-1099.json" "20-1098.json"]]
    (process-html (str "f:\\web-dict\\oxford\\htmls\\" filename) "f:\\web-dict\\oxford\\analysis" -parse)))
