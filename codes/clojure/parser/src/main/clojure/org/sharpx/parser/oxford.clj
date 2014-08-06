; clojure code to parse oxford dict html
(ns org.sharpx.parser.oxford
  ;  (:use [monger.core :only [connect get-db disconnect]])
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:use [org.sharpx.parser.htmlclean])
  (:use [clj-xpath.core :only [$x $x:tag $x:text $x:attrs $x:attrs* $x:node xml->doc]])
  (:use clojure.java.browse)
  (:require [clojure.java.io :as io])
  (:import org.sharpx.utils.FsUtils)
  (:import java.net.URL)
  (:import java.io.File)
  (:import java.util.HashMap)
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
               pos ($x:text "//div[contains(@class,\"webtop-g\")]/span[contains(@class,\"pos\")]" doc)]
           {:entry entry :pos pos}) ;#((println %) %)))
         (prn "index"))]
      (println new-values)
      (merge {:type type :url url :bc (count html)} new-values)))

  ([filepath,dest-dir]
    (let [fc (FsUtils/loadJson filepath (.getClass (HashMap.)) nil) ;(FsUtils/loadJson filepath (class HashMap) nil)
          inf (io/file filepath)
          fbn (->> (.getName inf) (re-find #"^[^\.]+"))
          outf (io/file dest-dir fbn (str fbn ".html"))
          type (.get fc "html") ;type (:type fc)
          url (.get fc "url") ;url (:url fc)
          html (.get fc "type")]
      (when-not (.exists outf)
        (io/make-parents outf) (spit outf html))
      (browse-url (.toString (.toURL outf)))
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
  (-parse "g:\\web-dict\\oxford\\htmls\\20-500.json" "g:\\web-dict\\oxford\\analysis"))
