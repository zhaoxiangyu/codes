(ns org.sharpx.parser.htmlgetter
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:require [clojure.java.io :as io])
  (:use [clj-xpath.core]
        clojure.java.browse org.sharpx.fs-util)
  (:import org.sharpx.utils.FsUtils java.net.URL java.io.File java.util.HashMap))

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
  (let [;files (file-seq src-dir)
        files (read-dir-files src-dir)
        files (sort-by #(.length %) < files)
        ;content (slurp (take 1 files))
        ]
    (doseq [[fn fs] (map (fn [file] [(.getName file) (.length file)]) files)]
      (println "filename:" fn ",size:" fs))))

(defn process-html
  ([filepath dest-dir parse]
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
      (parse {:type type :url url :html html}))))
