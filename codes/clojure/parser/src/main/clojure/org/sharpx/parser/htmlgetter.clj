(ns org.sharpx.parser.htmlgetter
  (:require [monger.core :as mg]
            [monger.collection :as mc]))

(defn from-mongo
  "processing url:html pairs in mongo database"
  [host port dbname cname parser]
  (let [conn (mg/connect {:host host :port port})
        db (mg/get-db conn dbname)
        rs (map parser (mc/find-maps db cname))]
    (last rs)
    (mg/disconnect conn)
    rs))

(defn from-fs
  "parse one file in src-dir randomly, store results to des-dir"
  [src-dir dest-dir parser]
  (let [files (file-seq src-dir)
        ;content (slurp (take 1 files))
        ]))

