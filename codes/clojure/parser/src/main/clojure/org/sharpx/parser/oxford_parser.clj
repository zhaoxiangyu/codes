; clojure code to parse oxford dict html
(ns org.sharpx.parser.oxford-parser
  (:require [clojure.string :as string])
  (:use [org.sharpx.parser htmlclean]
        [clj-xpath.core])
  (:gen-class))

(defn- parse-definitions
  [doc]
  (let [nodes ($x:node* "//div[@class=\"h-g\"]/span[@class=\"n-g\"]" doc)]
    (map-indexed
      (fn [i n]
        (let [ord ($x:text? (str "//div[@class=\"h-g\"]/span[@class=\"n-g\" and position()=" (inc i) "]" "/span[@class=\"z_n\"]") doc)
              plural ($x:text? (str "//div[@class=\"h-g\"]/span[@class=\"n-g\" and position()=" (inc i) "]" "/span[@class=\"a\"]") doc)
              gr ($x:text? (str "//div[@class=\"h-g\"]/span[@class=\"n-g\" and position()=" (inc i) "]" "/span[@class=\"z_gr\"]") doc)
              cf ($x:text? (str "//div[@class=\"h-g\"]/span[@class=\"n-g\" and position()=" (inc i) "]" "/span[@class=\"cf\"]") doc)
              g ($x:text? (str "//div[@class=\"h-g\"]/span[@class=\"n-g\" and position()=" (inc i) "]" "/span[@class=\"z_g\"]") doc)
              r ($x:text? (str "//div[@class=\"h-g\"]/span[@class=\"n-g\" and position()=" (inc i) "]" "/span[@class=\"z_r\"]") doc)
              defi ($x:text? (str "//div[@class=\"h-g\"]/span[@class=\"n-g\" and position()=" (inc i) "]" "/span[@class=\"d\"]") doc)]
          (array-map :n ord :plural plural :gr gr :cf cf :g g :r r :defi defi)))
      nodes)))

(defn- trimblanks
  [str]
  (cond
    (string? str) (string/trim (string/replace str #"[\n\t]+" " "))
    (= '() str) '()
    (seq? str) (map #(string/trim (string/replace % #"[\n\t]+" " ")) str)))

(defn- parse-term
  [html]
  (let [doc (->> (html-clean html)
              xml->doc)
        ;20-500 cabby 20-501 caber
        entry ($x:text "//div[@class=\"webtop-g\"]/h2" doc)
        pos ($x:text? "//div[@class=\"webtop-g\"]/span[@class=\"pos\"]" doc)
        also ($x:text? "//span[@class=\"vs-g\"]/span[@class=\"vs\" or @class=\"v\"]" doc)
        BrE ($x:text? "//div[@class=\"ei-g\"]/span[@class=\"i\"]" doc)
        BrE-mp3 (($x:attrs? "//div[@class=\"ei-g\"]/div[@class=\"sound audio_play_button pron-uk icon-audio\"]" doc) :data-src-mp3)
        NAmE ($x:text? "//div[@class=\"ei-g\"]/span[@class=\"y\"]" doc)
        NAmE-mp3 (($x:attrs? "//div[@class=\"ei-g\"]/div[@class=\"sound audio_play_button pron-us icon-audio\"]" doc) :data-src-mp3)
        plural ($x:text? "//span[@class=\"if-g\"]/span[@class=\"if\"]" doc)
        h-gr ($x:text* "//div[@class=\"top-g\"]/span[@class=\"z_gr\"]" doc)
        h-r ($x:text? "//div[@class=\"top-g\"]/span[@class=\"z_r\"]" doc)
        defi ($x:text? "//div[@class=\"def_block\"]/span[@class=\"d\"]" doc)
        ;20-1000
        defi (if (nil? (first defi)) ($x:text* "//div[@class=\"h-g\"]/span[@class=\"d\" or @class=\"dab\"]" doc) defi)
        defi (if (nil? (first defi)) (parse-definitions doc) defi)
        xr ($x:text* "//div[@class=\"h-g\"]/span[@class=\"xr-g\"]" doc)
        term (array-map :entry entry :pos pos :also also :BrE BrE :BrE-mp3 BrE-mp3 :NAmE NAmE :NAmE-mp3 NAmE-mp3
               :plural plural :defi defi :xr xr :h-gr h-gr :h-r h-r)]
    ;term
    (apply array-map
      (mapcat vector (keys term) (map trimblanks (vals term))))))

;/html/body//div[contains(@id,"entryContent")]
(defn -parse
  "parse url:html pair"
  [{:keys [type url html]} exception-handler] ;[{t :type url :url html :html}]
  (try
    (let [new-values (if (= type "e")
                       (parse-term html) ;#((println %) %)))
                       (prn "index"))
          ret (merge new-values (array-map :type type :url url :bc (count html)))]
      new-values)
    (catch Exception e
      (exception-handler) (throw e))))
