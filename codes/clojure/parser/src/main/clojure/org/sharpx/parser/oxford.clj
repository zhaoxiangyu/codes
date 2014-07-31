; clojure code to parse oxford dict html
(ns org.sharpx.parser.oxford
  ;  (:use [monger.core :only [connect get-db disconnect]])
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:gen-class))

(defn -htmls [host port dbname cname]

  (let [conn (mg/connect {:host host :port port})
        db (mg/get-db conn dbname)]

    (let [rs (mc/find-maps db cname)]
      (doall (map println rs))
      (mg/disconnect conn)
      )))

(defn -parse []
  (let [htmls (-htmls "169.254.10.41" 27017 "oxford" "htmls")]
    (println htmls)))

(defn -main [& argv]
  (println "oxford dict parser started!")
  (-parse))
