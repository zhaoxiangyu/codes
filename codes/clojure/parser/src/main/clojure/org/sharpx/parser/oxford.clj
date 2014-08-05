; clojure code to parse oxford dict html
(ns org.sharpx.parser.oxford
  ;  (:use [monger.core :only [connect get-db disconnect]])
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:use [org.sharpx.parser.htmlclean])
  (:use [clj-xpath.core :only [$x $x:tag $x:text $x:attrs $x:attrs* $x:node xml->doc]])
  (:require [clojure.java.io :as io])
  (:import org.sharpx.utils.FsUtils)
  (:import java.util.HashMap)
  (:gen-class))

;/html/body//div[contains(@id,"entryContent")]
(defn -parse
  "parse url:html pair"
  ([{:keys [type url html]}] ;[{t :type url :url html :html}]
    (->>
      (if (= type "e")
        (let [entry (->> (html-clean html)
                      xml->doc
                      ($x "//div[contains(@id,\"entryContent\")]"))]
          (println entry)) ;#((println %) %)))
        (prn "index"))
      ;println
      ;(str "aa")
      (conj [type url (count html)])))
  ([filepath,dest-dir]
    (let [fc (FsUtils/loadJson filepath (.getClass (HashMap.)) nil)
          type (.get fc "html") ;type (:type fc)
          url (.get fc "url") ;url (:url fc)
          html (.get fc "type")]
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
  (-parse "g:\\web-dict\\oxford\\htmls\\20-1000.json" "g:\\web-dict\\oxford\\analysis"))
