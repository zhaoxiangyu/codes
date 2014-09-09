(ns org.sharpx.fs-util
  (:import [java.io FileReader FileNotFoundException])
  (:import [java.util.zip ZipFile])
  (:import [java.io File FileOutputStream StringWriter PrintWriter IOException])
  (:import [org.apache.commons.io FileUtils])
  (:require [clojure [string :as str]])
  (:require [clojure.java.io :as io])
  (:use [org.sharpx misc log grmr-ext]))

(def file-path-separator
  (System/getProperty "file.separator"))

(def class-path-separator
  (System/getProperty "path.separator"))

(defmacro with-file-lock
  [path & body]
  `(let [f# (File. ~path)
         _# (.createNewFile f#)
         rf# (RandomAccessFile. f# "rw")
         lock# (.. rf# (getChannel) (lock))]
     (try
       ~@body
       (finally
         (.release lock#)
         (.close rf#)))))

(defn tokenize-path
  [^String path]
  (let [toks (.split path "/")]
    (vec (filter (complement empty?) toks))))

(defn parent-path
  [path]
  (let [toks (tokenize-path path)]
    (str "/" (str/join "/" (butlast toks)))))

(defn toks->path
  [toks]
  (str "/" (str/join "/" toks)))

(defn normalize-path
  [^String path]
  (toks->path (tokenize-path path)))

(defn full-path
  [parent name]
  (let [toks (tokenize-path parent)]
    (toks->path (conj toks name))))

(defn extract-dir-from-jar [jarpath dir destdir]
  (try-cause
    (with-open [jarpath (ZipFile. jarpath)]
      (let [entries (enumeration-seq (.entries jarpath))]
        (doseq [file (filter (fn [entry](and (not (.isDirectory entry)) (.startsWith (.getName entry) dir))) entries)]
          (.mkdirs (.getParentFile (File. destdir (.getName file))))
          (with-open [out (FileOutputStream. (File. destdir (.getName file)))]
            (io/copy (.getInputStream jarpath file) out)))))
    (catch IOException e
      (log-message "Could not extract " dir " from " jarpath))))

(defn exists-file?
  [path]
  (.exists (File. path)))

(defn rmr
  [path]
  (log-debug "Rmr path " path)
  (when (exists-file? path)
    (try
      (FileUtils/forceDelete (File. path))
      (catch FileNotFoundException e))))

(defn rmpath
  "Removes file or directory at the path. Not recursive. Throws exception on failure"
  [path]
  (log-debug "Removing path " path)
  (when (exists-file? path)
    (let [deleted? (.delete (File. path))]
      (when-not deleted?
        (throw (RuntimeException. (str "Failed to delete " path)))))))

(defn local-mkdirs
  [path]
  (log-debug "Making dirs at " path)
  (FileUtils/forceMkdir (File. path)))

(defn touch
  [path]
  (log-debug "Touching file at " path)
  (let [success? (do (if on-windows? (.mkdirs (.getParentFile (File. path))))
                   (.createNewFile (File. path)))]
    (when-not success?
      (throw (RuntimeException. (str "Failed to touch " path))))))

(defn read-dir-contents
  [dir]
  (if (exists-file? dir)
    (let [content-files (.listFiles (File. dir))]
      (map #(.getName ^File %) content-files))
    []))

(defn read-dir-files
  [dir]
  (if (exists-file? dir)
    (let [content-files (.listFiles (File. dir))]
      (filter #(.isFile ^File %) content-files))
    []))

(defn current-classpath
  []
  (System/getProperty "java.class.path"))

(defn add-to-classpath
  [classpath paths]
  (str/join class-path-separator (cons classpath paths)))

(defn zip-contains-dir?
  [zipfile target]
  (let [entries (->> zipfile (ZipFile.) .entries enumeration-seq (map (memfn getName)))]
    (some? #(.startsWith % (str target "/")) entries)))

(defn copy-uri-to-file [uri file]
  (with-open [in (io/input-stream uri)
              out (io/output-stream file)]
    (io/copy in out)))