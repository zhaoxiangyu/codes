; clojure code to parse oxford dict html
(ns org.sharpx.parser.oxford-parser
  (:require [clojure.string :as string])
  (:use [org.sharpx.parser htmlclean]
        [clj-xpath.core])
  (:gen-class))

(def term-head "head" "//div[@class=\"entry\"]/div[@class=\"h-g\"]")
(def term-block "block?" "//div[@class=\"entry\"]/span[@class=\"block-g\"]")
(def term-morphs "morph" "//div[@class=\"entry\"]/span[@class=\"ifs-g\"]")
(def term-help "help" "//div[@class=\"entry\"]/span[@class=\"help\"]")
(def term-explanations "explanations" "//div[@class=\"entry\"]/div[@class=\"sd-g\"]")
(def term-idoms "idoms" "//div[@class=\"entry\"]/span[@class=\"ids-g\"]")
(def term-phverbs "phrase verbs" "//div[@class=\"entry\"]/div[@class=\"pvp-g\"]")
(def term-usages "usage notes" "//div[@class=\"entry\"]/span[@class=\"unbox\"]")

(defn- parse-definitions
  [doc prefix]
  (map-indexed
    (fn [i n]
      (let [prefix (str prefix "[position()=" (inc i) "]")
            sd ($x:text? (str prefix "/h3[@class=\"sd\"]") doc)
            nodes ($x:node* (str prefix "/span[@class=\"n-g\"]") doc)
            ngs (map-indexed
                  (fn [i n]
                    (let [prefix (str prefix "/span[@class=\"n-g\" and position()=" (inc i) "]")
                          ord ($x:text? (str prefix "/span[@class=\"z_n\"]") doc)
                          plural ($x:text? (str prefix "/span[@class=\"a\"]") doc)
                          gr ($x:text? (str prefix "/span[@class=\"z_gr\"]") doc)
                          cf ($x:text? (str prefix "/span[@class=\"cf\"]") doc)
                          g ($x:text? (str prefix "/span[@class=\"z_g\"]") doc)
                          r ($x:text? (str prefix "/span[@class=\"z_r\"]") doc)
                          defi ($x:text? (str prefix "/span[@class=\"d\"]") doc)
                          examples (map-indexed
                                     (fn [i x]
                                       (let [prefix (str prefix "/span[@class=\"x-g\" and position()=" (inc i) "]")
                                             cf ($x:text? (str prefix "/span[@class=\"cf\"]") doc)
                                             x ($x:text? (str prefix "/span[@class=\"x\"]") doc)]
                                         (array-map :n i :cf cf :x x)))
                                     ($x:node* (str prefix "/span[@class=\"x-g\"]") doc))]
                      (array-map :n ord :plural plural :gr gr :cf cf :g g :r r :defi defi :examples examples)))
                  nodes)]
        #_ (println "prefix:" prefix)
        (if (and (nil? sd) (empty? ngs))
          nil (array-map :sd sd :ngs ngs))))
    ($x:node* prefix doc)))

(defn- parse-morphs
  [doc]
  (let [nodes ($x:node* (str term-morphs "/span[@class=\"if-g\"]/span[@class=\"if\"]") doc)]
    (map-indexed
      (fn [i n]
        (let [morph ($x:text? (str term-morphs "//span[@class=\"if\" and position()=" (inc i) "]") doc)
              BrE ($x:text? (str term-morphs "//div[@class=\"ei-g\" and position()=" (inc i) "]/span[@class=\"i\"]") doc)
              BrE-mp3 (($x:attrs? (str term-morphs "//div[@class=\"ei-g\" and position()=" (inc i) "]/div[@class=\"sound audio_play_button pron-uk icon-audio\"]") doc) :data-src-mp3)
              NAmE ($x:text? (str term-morphs "//div[@class=\"ei-g\" and position()=" (inc i) "]/span[@class=\"y\"]") doc)
              NAmE-mp3 (($x:attrs? (str term-morphs "//div[@class=\"ei-g\" and position()=" (inc i) "]/div[@class=\"sound audio_play_button pron-us icon-audio\"]") doc) :data-src-mp3)]
          (array-map :n i :morph morph :BrE BrE :BrE-mp3 BrE-mp3 :NAmE NAmE :NAmE-mp3 NAmE-mp3)))
      nodes)))

(defn- trimblanks
  [str]
  (cond
    (string? str) (string/trim (string/replace str #"[\n\t]+" " "))
    (= '() str) '()
    (seq? str) (map trimblanks str)))

(defn- parse-term
  [html]
  (let [doc (->> (html-clean html)
              ((fn [str] (println str) str))
              xml->doc)
        ;term head
        entry ($x:text (str term-head "//div[@class=\"webtop-g\"]/h2") doc)
        pos ($x:text? (str term-head "//div[@class=\"webtop-g\"]/span[@class=\"pos\"]") doc)
        also ($x:text? (str term-head "//span[@class=\"vs-g\"]/span[@class=\"vs\" or @class=\"v\"]") doc)
        BrE ($x:text? (str term-head "//div[@class=\"ei-g\"]/span[@class=\"i\"]") doc)
        BrE-mp3 (($x:attrs? (str term-head "//div[@class=\"ei-g\"]/div[@class=\"sound audio_play_button pron-uk icon-audio\"]") doc) :data-src-mp3)
        NAmE ($x:text? (str term-head "//div[@class=\"ei-g\"]/span[@class=\"y\"]") doc)
        NAmE-mp3 (($x:attrs? (str term-head "//div[@class=\"ei-g\"]/div[@class=\"sound audio_play_button pron-us icon-audio\"]") doc) :data-src-mp3)
        h-gr ($x:text* (str term-head "//div[@class=\"top-g\"]/span[@class=\"z_gr\"]") doc)
        h-r ($x:text? (str term-head "//div[@class=\"top-g\"]/span[@class=\"z_r\"]") doc)
        defi ($x:text? (str term-head "/div[@class=\"def_block\"]/span[@class=\"d\"]") doc)
        defi (if (nil? (first defi)) ($x:text* (str term-head "/span[@class=\"d\" or @class=\"dab\"]") doc) defi)
        defi (if (nil? (first defi)) (parse-definitions doc term-head) defi)
        xr ($x:text* (str term-head "/span[@class=\"xr-g\"]") doc) ;bushel 20-1100 idoms
        plural ($x:text? (str term-head "//span[@class=\"if-g\"]/span[@class=\"if\"]") doc)
        ;term morphs
        morphs (parse-morphs doc)
        help ($x:text* term-help doc)
        defi (if (nil? (first defi)) (parse-definitions doc term-explanations) defi)
        _ (pr defi)

        term (array-map :entry entry :pos pos :also also :BrE BrE :BrE-mp3 BrE-mp3 :NAmE NAmE :NAmE-mp3 NAmE-mp3
               :plural plural :defi defi :xr xr :h-gr h-gr :h-r h-r :morphs morphs :help help)]
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
