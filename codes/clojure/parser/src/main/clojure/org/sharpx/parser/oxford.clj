; clojure code to parse oxford dict html
(ns org.sharpx.parser.oxford
  ;  (:use [monger.core :only [connect get-db disconnect]])
  (:require [monger.core :as mg] [monger.collection :as mc] [clojure.java.io :as io])
  (:use [org.sharpx.parser.htmlclean] [clj-xpath.core :only [$x $x:tag $x:text $x:attrs $x:attrs* $x:node xml->doc]]
        clojure.java.browse)
  (:import org.sharpx.utils.FsUtils java.net.URL java.io.File java.util.HashMap)
  (:gen-class))

;/html/body//div[contains(@id,"entryContent")]
(defn -parse
  "parse url:html pair"
  ([{:keys [type url html]}] ;[{t :type url :url html :html}]
    (let
      [new-values
       (if (= type "e")
         (let [doc (->> (html-clean html)
                     xml->doc)
               entry ($x:text "//div[contains(@class,\"webtop-g\")]/h2" doc)
               pos ($x:text "//div[contains(@class,\"webtop-g\")]/span[contains(@class,\"pos\")]" doc)
               also ($x:text "//span[contains(@class,\"vs-g\")]/span[@class=\"vs\"]" doc)
               BrE ($x:text "//div[@class=\"ei-g\"]/span[@class=\"i\"]" doc)
               BrE-mp3 ($x:attrs* "//div[@class=\"ei-g\"]/div[@class=\"sound audio_play_button pron-uk icon-audio\"]" doc :data-src-mp3)
               NAmE ($x:text "//div[@class=\"ei-g\"]/span[@class=\"y\"]" doc)
               NAmE-mp3 ($x:attrs* "//div[@class=\"ei-g\"]/div[@class=\"sound audio_play_button pron-us icon-audio\"]" doc :data-src-mp3)
               plural ($x:text "//span[@class=\"if-g\"]/span[@class=\"if\"]" doc)
               defi ($x:text "//div[@class=\"def_block\"]/span[@class=\"d\"]" doc)]
           {:entry entry :pos pos :also also :BrE BrE :BrE-mp3 BrE-mp3 :NAmE NAmE :NAmE-mp3 NAmE-mp3
            :plural plural :defi defi}) ;#((println %) %)))
         (prn "index"))]
      (println new-values)
      (merge {:type type :url url :bc (count html)} new-values)))
  ([filepath,dest-dir]
    (let [fc (FsUtils/loadJson filepath (.getClass (HashMap.)) nil) ;(FsUtils/loadJson filepath (class HashMap) nil)
          type (.get fc "html") ;type (:type fc)
          url (.get fc "url") ;url (:url fc)
          html (.get fc "type")
          ;file info
          inf (io/file filepath)
          fbn (->> (.getName inf) (re-find #"^[^\.]+"))
          outf (io/file dest-dir fbn (str fbn ".html")) ]
      (when-not (.exists outf)
        (io/make-parents outf) (spit outf html))
      #_(browse-url (.toString (.toURL outf)))
      ;(println "url:" url "html:" html)
      (-parse {:type type :url url :html html}))))

(defn -htmls
  "processing url:html pairs in mongo database"
  [host port dbname cname parser]
  (let [conn (mg/connect {:host host :port port})
        db (mg/get-db conn dbname)
        rs (map parser (mc/find-maps db cname))]
    (last rs)
    (mg/disconnect conn)
    rs))

(defn rand-parse
  "parse one file in src-dir randomly, store results to des-dir"
  [src-dir dest-dir]
  (let [files (file-seq src-dir)
        ;content (slurp (take 1 files))
        ]))

(defn -main [& argv]
  (println "oxford dict parser started!")
  #_ (let [htmls (-htmls "169.254.244.218" 27017 "oxford" "htmls" -parse)]
       (dorun (map println htmls)))
  #_ (rand-parse "g:\\web-dict\\oxford\\htmls" "g:\\web-dict\\oxford\\analysis")
  #_(-parse "g:\\web-dict\\oxford\\htmls\\20-1000.json" "g:\\web-dict\\oxford\\analysis")
  (-parse "g:\\web-dict\\oxford\\htmls\\20-500.json" "g:\\web-dict\\oxford\\analysis"))
