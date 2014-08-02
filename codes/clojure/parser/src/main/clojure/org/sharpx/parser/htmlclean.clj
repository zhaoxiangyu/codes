(ns org.sharpx.parser.htmlclean
  (:use [org.sharpx log])
  (:import [org.htmlcleaner HtmlCleaner PrettyXmlSerializer CleanerProperties]))

(defn html-clean
  "convert maliformed html to xml"
  [^String html-str]
  (try
    (when html-str
      (let [cleaner (new HtmlCleaner)]
        (doto (.getProperties cleaner) ;; set HtmlCleaner properties
          (.setOmitComments true)
          (.setPruneTags "script,style"))
        (let [serializer (PrettyXmlSerializer. (.getProperties cleaner))]
          (when-let [node (.clean cleaner html-str)]
            (log-debug node)
            (.getAsString serializer node))
          )))
    (catch Exception e
      (log-error e "Error when parsing"))))