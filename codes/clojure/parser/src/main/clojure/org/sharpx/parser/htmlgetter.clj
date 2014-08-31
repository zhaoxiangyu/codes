(ns org.sharpx.parser.htmlgetter
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [clojure.java.io :as io])
  (:use clj-xpath.core clojure.data clojure.java.browse clojure.pprint
        [org.sharpx fs-util ds-util misc])
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
  [term output-dir browse-html]
  (pprint term)
  (let [entry (:entry term)
        str-term (pr-str term)
        term-file (io/file output-dir (str entry ".term"))]
    (if (.exists term-file)
      (let [fc (slurp term-file)
            lterm (read-string fc)
            term-diff (diff term lterm)
            ret (= (vec (take 2 term-diff)) [nil nil])]
        (if ret
          (do (println "OK validation!") true)
          (do (browse-html)
            (println "FAILED validation!")
            (println "new values:" (first term-diff))
            (println "old values:" (second term-diff))
            (mconfirm "Accept new parse result and overwrite file?"
              [(spit term-file str-term)
               (when (.exists term-file)
                 (println "parse result updated to " (.getPath term-file)))
               true]
              [(println "you have denied above parse result!")
               false]))))
      (do (browse-html)
        (mconfirm "Is above parse result correct?"
          [(spit term-file str-term)
           (when (.exists term-file)
             (println "parse result saved to " (.getPath term-file)))
           true]
          [(println "you have denied above parse result!")
           false])))))

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
          outf (io/file dest-dir fbn (str fbn ".html"))
          outxml (io/file dest-dir fbn (str fbn ".xml"))]
      (try
        (when-not (.exists outf)
          (io/make-parents outf) (spit outf html))
        ;(println "url:" url "html:" html)
        (let [term (parse {:type type :url url :html html}
                     (fn [xml] (spit outxml xml) xml)
                     #(browse-url (.toString (.toURL outf))))]
          (if (nil? term)
            true
            (validate term (str dest-dir file-path-separator fbn) #(browse-url (.toString (.toURL outf))))))
        (catch Exception e (.printStackTrace e)
          (mconfirm "Want to continue?" [true] [false]))))))

(defn gen-snap
  [src-dir snap-file]
  (let [;files (file-seq src-dir)
        files (read-dir-files src-dir)
        fs (count files)
        _ (println "files count:" fs)
        files (sort-by #(.length %) < files)
        files (map (fn [file] {:fn (.getName file) :fs (.length file)}) files)
        snap {:dir src-dir :files files}]
    (spit snap-file (pr-str snap))
    (println "saved dir snap to file" (.getPath snap-file))
    snap))

(defn from-fs
  "parse one file in src-dir randomly, store results to des-dir"
  [src-dir dest-dir parser]
  (let [snap-file (io/file dest-dir "source-dir.snap")
        snap (if (.exists snap-file)
               (read-string (slurp snap-file))
               (gen-snap src-dir snap-file))]
    (peel (:files snap)
      (fn [{:keys [fn fs]}]
        (println "filename:" (toks->path [(:dir snap) fn]) ",size:" fs)
        (process-html (toks->path [(:dir snap) fn]) dest-dir parser)))
    #_ (doseq [{:keys [fn fs]} (take 30 (peel (:files snap)))]
         (println "filename:" (str (:dir snap) file-path-separator fn) ",size:" fs))))