; clojure code to parse oxford dict html
(ns org.sharpx.parser.oxford-parser
  ;  (:use [monger.core :only [connect get-db disconnect]])
  (:require [clojure.java.io :as io])
  (:use [org.sharpx.parser htmlclean]
        [clj-xpath.core]
        clojure.java.browse org.sharpx.fs-util)
  (:import org.sharpx.utils.FsUtils java.net.URL java.io.File java.util.HashMap)
  (:gen-class))

(defn- validate
  "validate term"
  [term doflag]
  (prn term))

;/html/body//div[contains(@id,"entryContent")]
(defn -parse
  "parse url:html pair"
  ([{:keys [type url html]}] ;[{t :type url :url html :html}]
    (let
      [new-values
       (if (= type "e")
         (let [doc (->> (html-clean html)
                     xml->doc)
               ;20-500 cabby 20-501 caber
               entry ($x:text "//div[@class=\"webtop-g\"]/h2" doc)
               pos ($x:text? "//div[@class=\"webtop-g\"]/span[@class=\"pos\"]" doc)
               also ($x:text? "//span[@class=\"vs-g\"]/span[@class=\"vs\"]" doc)
               BrE ($x:text? "//div[@class=\"ei-g\"]/span[@class=\"i\"]" doc)
               BrE-mp3 ($x:attrs* "//div[@class=\"ei-g\"]/div[@class=\"sound audio_play_button pron-uk icon-audio\"]" doc :data-src-mp3)
               NAmE ($x:text? "//div[@class=\"ei-g\"]/span[@class=\"y\"]" doc)
               NAmE-mp3 ($x:attrs* "//div[@class=\"ei-g\"]/div[@class=\"sound audio_play_button pron-us icon-audio\"]" doc :data-src-mp3)
               plural ($x:text? "//span[@class=\"if-g\"]/span[@class=\"if\"]" doc)
               h-gr ($x:text? "//div[@class=\"top-g\"]/span[@class=\"z_gr\"]" doc)
               h-r ($x:text? "//div[@class=\"top-g\"]/span[@class=\"z_r\"]" doc)
               defi ($x:text? "//div[@class=\"def_block\"]/span[@class=\"d\"]" doc)
               ;20-1000
               defi (if (nil? (first defi)) ($x:text* "//div[@class=\"h-g\"]/span[@class=\"d\" or @class=\"dab\"]" doc))
               defi (if (nil? (first defi))
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
                              {:n ord :plural plural :gr gr :cf cf :g g :r r :defi defi}))
                          nodes)))
               xr ($x:text* "//div[@class=\"h-g\"]/span[@class=\"xr-g\"]" doc)
               term (merge {:entry entry :pos pos :also also :BrE BrE :BrE-mp3 BrE-mp3 :NAmE NAmE :NAmE-mp3 NAmE-mp3
                            :plural plural :defi defi}
                      {:xr xr} {:h-gr h-gr :h-r h-r})]
           #_ (validate term false)
           term) ;#((println %) %)))
         (prn "index"))
       ret (merge {:type type :url url :bc (count html)} new-values)]
      (prn ret)
      ret))
  ([filepath,dest-dir]
    (let [fc (FsUtils/loadJson filepath (.getClass (HashMap.)) nil) ;(FsUtils/loadJson filepath (class HashMap) nil)
          type (.get fc "html") ;type (:type fc)
          url (.get fc "url") ;url (:url fc)
          html (.get fc "type")
          ;file info
          inf (io/file filepath)
          fbn (->> (.getName inf) (re-find #"^[^\.]+"))
          outf (io/file dest-dir fbn (str fbn ".html"))]
      (when-not (.exists outf)
        (io/make-parents outf) (spit outf html))
      (browse-url (.toString (.toURL outf)))
      ;(println "url:" url "html:" html)
      (-parse {:type type :url url :html html}))))

