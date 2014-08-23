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

(defn- validate
  "validate term"
  [term output-dir]
  (prn term)
  (let [entry (:entry term)
        term-file (io/file output-dir entry ".term")]
    (if (.exists term-file)
      (let [fc (slurp term-file)
            lterm (read-string fc)
            ret (compare term lterm)]
        (if ret (println "OK validation!") (println "FAILED validation!")))
      (let [str-term (pr-str term)
            input (do (println "Is above parse result correct?y/n") (read-line))]
        (if (or (= input "y") (= input "Y"))
          (do (spit term-file str-term)
              (when (.exists term-file)
                (println "parse result saved to " (.getPath term-file))))
          (do (println "you have denied above parse result!")))))))

(defn process-html
  ([filepath dest-dir parse]
    (println "processing html " filepath)
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
      #_ (browse-url (.toString (.toURL outf)))
      ;(println "url:" url "html:" html)
      (let [term (parse {:type type :url url :html html})]
        (validate term (str dest-dir file-path-separator fbn))))))

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