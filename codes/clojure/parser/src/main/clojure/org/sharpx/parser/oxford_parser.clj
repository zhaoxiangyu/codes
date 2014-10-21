; clojure code to parse oxford dict html
(ns org.sharpx.parser.oxford-parser
  (:require [clojure.string :as string])
  (:use [org.sharpx.parser htmlclean]
        [clj-xpath.core] clojure.pprint)
  (:gen-class))

(def term-head "head" "//div[@class=\"entry\"]/div[@class=\"h-g\"]")
(def term-block "block?" "//div[@class=\"entry\"]/span[@class=\"block-g\"]")
(def term-morphs "morph" "//div[@class=\"entry\"]/span[@class=\"ifs-g\"]")
(def term-help "help" "//div[@class=\"entry\"]/span[@class=\"help\"]")
(def term-explanations "explanations" "//div[@class=\"entry\"]/div[@class=\"sd-g\"]")
(def term-idoms "idoms" "//div[@class=\"entry\"]/span[@class=\"ids-g\"]")
(def term-phverbs "phrase verbs" "//div[@class=\"entry\"]/div[@class=\"pvp-g\"]")
(def term-usages "usage notes" "//div[@class=\"entry\"]/span[@class=\"unbox\"]")
(def audio-uk "sound audio_play_button pron-uk icon-audio")
(def audio-us "sound audio_play_button pron-us icon-audio")

(declare parse-definitions)
(declare parse-coll)

(defn- trimblanks
  [symb]
  (cond
    (string? symb) (string/trim (string/replace symb #"[\n\t]+" " "))
    (= '() symb) '()
    (number? symb) symb
    (seq? symb) (map trimblanks symb)
    (map? symb) (into (empty symb) (for [[k v] symb] [k (trimblanks v)]))))

(defn- conj-no-override
  [coll [key-name value]]
  (let [v (get coll key-name nil)]
    (if (and (map? coll) (or (empty? v) (nil? v)))
      (conj coll [key-name value])
      coll)))

(defn- parse-coll
  "parse-coll doc '/docs' '/doc[@class='xx']' [:title '/name'] [:subtitle '/subtitle' [:xx 'xx']]
  returns:
  [{:n 1 :title 'haha' :subtitle [{:xx 'yy'}]} {:n 2 :title 'haha2' :subtitle [{:xx 'yy2'}]}]"
  [doc prefix item-tag & sons]
  (let [nodes ($x:node* (str prefix item-tag) doc)
        r (map-indexed
            (fn [i _]
              (let [prefix (str prefix item-tag "[" (inc i) "]")
                    temp (reduce conj-no-override (array-map)
                           (map (fn [[key-name item-tag & structure]]
                                  (cond
                                    (empty? structure) (if (re-find #"_s$" (str key-name))
                                                         [key-name ($x:text* (str prefix item-tag) doc)]
                                                         [key-name ($x:text? (str prefix item-tag) doc)])
                                    (not (empty? structure)) [key-name (apply parse-coll doc prefix item-tag structure)]))
                             sons))
                    fres (conj temp [:n (inc i)])]
                (if (every? #(or (nil? %) (empty? %)) (vals temp)) nil
                  fres)))
            nodes)]
    (if (every? nil? r) nil
      r)))

(defn- parse-morphs
  [doc prefix]
  (let [nodes ($x:node* (str prefix "/span[@class=\"if-g\"]/span[@class=\"if\"]") doc)]
    (map-indexed
      (fn [i _]
        (let [morph ($x:text? (str prefix "//span[@class=\"if\"][" (inc i) "]") doc)
              BrE ($x:text? (str prefix "//div[@class=\"ei-g\"][" (inc i) "]/span[@class=\"i\"]") doc)
              BrE-mp3 (($x:attrs? (str prefix "//div[@class=\"ei-g\"][" (inc i) "]/div[@class=\"" audio-uk "\"]") doc) :data-src-mp3)
              NAmE ($x:text? (str prefix "//div[@class=\"ei-g\"][" (inc i) "]/span[@class=\"y\"]") doc)
              NAmE-mp3 (($x:attrs? (str prefix "//div[@class=\"ei-g\"][" (inc i) "]/div[@class=\"" audio-us "\"]") doc) :data-src-mp3)]
          (array-map :n i :morph morph :BrE BrE :BrE-mp3 BrE-mp3 :NAmE NAmE :NAmE-mp3 NAmE-mp3)))
      nodes)))

(def term-defi "definition"
  [[:sd "/h3[@class=\"sd\"]"]
   [:ngs "/span[@class=\"n-g\"]"
    [:ord "/span[@class=\"z_n\"]"] [:plural "/span[@class=\"a\""] [:gr_s "/span[@class=\"z_gr\""]
    [:cf "/span[@class=\"cf\"]"] [:g "/span[@class=\"z_g\"]"] [:r "/span[@class=\"z_r\"]"] [:defi "/span[@class=\"d\"]"]
    [:examples "/span[@class=\"x-g\"]"
     [:cf "/span[@class=\"cf\"]"] [:x "/span[@class=\"x\"]"]]]])

(defmacro parse-term
  [html xmlstr-handler]
  `(let [doc (->> (html-clean ~html)
               ~xmlstr-handler
               xml->doc)
         head (parse-coll doc "//div[@class=\"entry\"]" "/div[@class=\"h-g\"]"
                [:entry "//div[@class=\"webtop-g\"]/h2"] [:pos "//div[@class=\"webtop-g\"]/span[@class=\"pos\"]"] [:also "//span[@class=\"vs-g\"]/span[@class=\"vs\" or @class=\"v\"]"]
                [:BrE "//div[@class=\"ei-g\"]/span[@class=\"i\"]"] [:BrE-mp3 ""]
                [:NAmE "//div[@class=\"ei-g\"]/span[@class=\"y\"]"] [:NAmE-mp3 ""]
                [:h-gr "//div[@class=\"top-g\"]/span[@class=\"z_gr\"]"] [:h-r "//div[@class=\"top-g\"]/span[@class=\"z_r\"]"]
                [:defi "/div[@class=\"def_block\"]/span[@class=\"d\"]"] [:defi "/span[@class=\"d\" or @class=\"dab\"]"]
                [:defi "//div[@class=\"entry\"]" "/div[@class=\"h-g\"]" ~@term-defi]
                [:xr_s "/span[@class=\"xr-g\"]"] [:plural "//span[@class=\"if-g\"]/span[@class=\"if\"]"]
                [:pic-url "//div[@id=\"ox-enlarge\"]/a[@class=\"topic\"]"])
         morphs (parse-morphs doc ~term-morphs)
         help ($x:text* ~term-help doc)



         
         defi (:defi head)
         defi (if (or (nil? defi) (empty? defi))
                (parse-coll doc "//div[@class=\"entry\"]" "/div[@class=\"sd-g\"]" ~@term-defi) defi)
         idoms (parse-coll doc "//div[@class=\"entry\"]/span[@class=\"ids-g\"]" "/div[@class=\"id-g\"]"
                 [:cm "/span[@class=\"cm\"]"] [:h4_s "/h4|/z"] [:zr "/span[@class=\"z_r\"]"]
                 [:defi "/div[@class=\"def_block\"]/span[@class=\"ud\" or @class=\"d\"]"] [:defi "/span[@class=\"d\" or @class=\"dab\"]"]
                 [:xgs "/span[@class=\"x-g\"]"
                  [:example "/span[@class=\"x\"]"]])

         term (array-map :head head :morphs morphs :help help :defi defi :idoms idoms)]
     ;term
     (apply array-map
       (mapcat vector (keys term) (map trimblanks (vals term))))))

;/html/body//div[contains(@id,"entryContent")]
(defn -parse
  "parse url:html pair"
  [{:keys [type url html]} xml-writer exception-handler] ;[{t :type url :url html :html}]
  (try
    (let [new-values
          (if (= type "e")
            (parse-term html xml-writer) ;#((println %) %)))
            (prn "it's category page,not term page!"))
          ret (merge new-values (array-map :type type :url url :bc (count html)))]
      new-values)
    (catch Exception e
      (exception-handler) (throw e))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;old implementation;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

#_ (defn- parse-idoms
     [doc prefix]
     (let [prefix (str prefix "/div[@class=\"id-g\"]")
           nodes ($x:node* prefix doc)]
       (map-indexed
         (fn [i _]
           (let [prefix (str prefix "[" (inc i) "]")
                 cm ($x:text? (str prefix "/span[@class=\"cm\"]") doc)
                 h4 ($x:text* (str prefix "/h4|z") doc)
                 zr ($x:text? (str prefix "/span[@class=\"z_r\"]") doc)
                 defi ($x:text? (str prefix "/div[@class=\"def_block\"]/span[@class=\"ud\" or @class=\"d\"]") doc)
                 defi (if (or (nil? defi) (empty? defi)) ($x:text* (str prefix "/span[@class=\"d\" or @class=\"dab\"]") doc) defi)
                 xgs (parse-coll doc prefix "/span[@class=\"x-g\"]" [:example "/span[@class=\"x\"]"])
                 #_ (ngs)]
             (array-map :n (inc i) :cm cm :h4 h4 :zr zr :defi defi :xgs xgs)))
         nodes)))

#_ (defn- parse-definitions
     [doc prefix]
     (filter (comp not nil?)
       (map-indexed
         (fn [i _]
           (let [prefix (str prefix "[" (inc i) "]")
                 sd ($x:text? (str prefix "/h3[@class=\"sd\"]") doc)
                 nodes ($x:node* (str prefix "/span[@class=\"n-g\"]") doc)
                 ngs (map-indexed
                       (fn [i _]
                         (let [i (inc i)
                               prefix (str prefix "/span[@class=\"n-g\"][" i "]")
                               ord ($x:text? (str prefix "/span[@class=\"z_n\"]") doc)
                               plural ($x:text? (str prefix "/span[@class=\"a\"]") doc)
                               gr ($x:text* (str prefix "/span[@class=\"z_gr\"]") doc)
                               cf ($x:text? (str prefix "/span[@class=\"cf\"]") doc)
                               g ($x:text? (str prefix "/span[@class=\"z_g\"]") doc)
                               r ($x:text? (str prefix "/span[@class=\"z_r\"]") doc)
                               defi ($x:text? (str prefix "/span[@class=\"d\"]") doc)
                               examples (map-indexed
                                          (fn [i _]
                                            (let [prefix (str prefix "/span[@class=\"x-g\"][" (inc i) "]")
                                                  cf ($x:text? (str prefix "/span[@class=\"cf\"]") doc)
                                                  x ($x:text? (str prefix "/span[@class=\"x\"]") doc)]
                                              (array-map :n (inc i) :cf cf :x x)))
                                          ($x:node* (str prefix "/span[@class=\"x-g\"]") doc))]
                           (array-map :n ord :plural plural :gr gr :cf cf :g g :r r :defi defi :examples examples)))
                       nodes)]
             #_ (println "prefix:" prefix)
             (if (and (nil? sd) (empty? ngs))
               nil (array-map :sd sd :ngs ngs))))
         ($x:node* prefix doc))))

#_ (defn- parse-head
     [doc prefix]
     (let [entry ($x:text (str prefix "//div[@class=\"webtop-g\"]/h2") doc)
           pos ($x:text? (str prefix "//div[@class=\"webtop-g\"]/span[@class=\"pos\"]") doc)
           also ($x:text? (str prefix "//span[@class=\"vs-g\"]/span[@class=\"vs\" or @class=\"v\"]") doc)
           BrE ($x:text? (str prefix "//div[@class=\"ei-g\"]/span[@class=\"i\"]") doc)
           BrE-mp3 (:data-src-mp3 ($x:attrs? (str prefix "//div[@class=\"ei-g\"]/div[@class=\"sound audio_play_button pron-uk icon-audio\"]") doc))
           NAmE ($x:text? (str prefix "//div[@class=\"ei-g\"]/span[@class=\"y\"]") doc)
           NAmE-mp3 (:data-src-mp3 ($x:attrs? (str prefix "//div[@class=\"ei-g\"]/div[@class=\"sound audio_play_button pron-us icon-audio\"]") doc))
           h-gr ($x:text* (str prefix "//div[@class=\"top-g\"]/span[@class=\"z_gr\"]") doc)
           h-r ($x:text? (str prefix "//div[@class=\"top-g\"]/span[@class=\"z_r\"]") doc)
           defi ($x:text? (str prefix "/div[@class=\"def_block\"]/span[@class=\"d\"]") doc)
           defi (if (or (nil? defi) (empty? defi)) ($x:text* (str prefix "/span[@class=\"d\" or @class=\"dab\"]") doc) defi)
           defi (if (or (nil? defi) (empty? defi))
                  (parse-coll doc "//div[@class=\"entry\"]" "/div[@class=\"h-g\"]"
                    [:sd "/h3[@class=\"sd\"]"]
                    [:ngs "/span[@class=\"n-g\"]"
                     [:ord "/span[@class=\"z_n\"]"] [:plural "/span[@class=\"a\""] [:gr_s "/span[@class=\"z_gr\""]
                     [:cf "/span[@class=\"cf\"]"] [:g "/span[@class=\"z_g\"]"] [:r "/span[@class=\"z_r\"]"] [:defi "/span[@class=\"d\"]"]
                     [:examples "/span[@class=\"x-g\"]"
                      [:cf "/span[@class=\"cf\"]"] [:x "/span[@class=\"x\"]"]]])
                  defi)
           xr ($x:text* (str prefix "/span[@class=\"xr-g\"]") doc) ;bushel 20-1100 idoms
           plural ($x:text? (str prefix "//span[@class=\"if-g\"]/span[@class=\"if\"]") doc)
           pic-url (:href ($x:attrs? (str prefix "//div[@id=\"ox-enlarge\"]/a[@class=\"topic\"]") doc))]
       (array-map :entry entry :pos pos :also also :BrE BrE :BrE-mp3 BrE-mp3 :NAmE NAmE :NAmE-mp3 NAmE-mp3
         :plural plural :defi defi :xr xr :h-gr h-gr :h-r h-r :pic-url pic-url)))

