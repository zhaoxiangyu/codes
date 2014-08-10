(ns org.sharpx.parser.main
  (:use [org.sharpx.parser htmlgetter oxford-parser])
  (:gen-class))

(defn -main [& argv]
  (println "oxford dict parser started!")
  #_ (let [htmls (from-mongo "169.254.244.218" 27017 "oxford" "htmls" -parse)]
       (dorun (map println htmls)))
  #_ (from-fs "g:\\web-dict\\oxford\\htmls" "g:\\web-dict\\oxford\\analysis" -parser)
  #_ (-parse "g:\\web-dict\\oxford\\htmls\\20-1000.json" "g:\\web-dict\\oxford\\analysis")
  #_ (-parse "g:\\web-dict\\oxford\\htmls\\20-500.json" "g:\\web-dict\\oxford\\analysis")
  #_ (-parse "g:\\web-dict\\oxford\\htmls\\20-501.json" "g:\\web-dict\\oxford\\analysis")
  #_ (-parse "g:\\web-dict\\oxford\\htmls\\20-502.json" "g:\\web-dict\\oxford\\analysis")
  #_ (-parse "g:\\web-dict\\oxford\\htmls\\20-503.json" "g:\\web-dict\\oxford\\analysis")
  #_ (-parse "g:\\web-dict\\oxford\\htmls\\20-600.json" "g:\\web-dict\\oxford\\analysis")
  #_ (-parse "g:\\web-dict\\oxford\\htmls\\20-1000.json" "g:\\web-dict\\oxford\\analysis")
  #_ (-parse "g:\\web-dict\\oxford\\htmls\\20-1100.json" "g:\\web-dict\\oxford\\analysis")
  #_ (-parse "g:\\web-dict\\oxford\\htmls\\20-1099.json" "g:\\web-dict\\oxford\\analysis")
  (-parse "g:\\web-dict\\oxford\\htmls\\20-1098.json" "g:\\web-dict\\oxford\\analysis"))
