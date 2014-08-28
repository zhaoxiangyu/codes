(ns org.sharpx.parser.htmlclean
  (:use [org.sharpx log])
  (:import [org.htmlcleaner HtmlCleaner PrettyXmlSerializer CleanerProperties]))

(defn html-clean
  "convert maliformed html to xml"
  [^String html-str]
  (when html-str
    #_(println html-str)
    (let [cleaner (new HtmlCleaner)
          cleanprop (doto (.getProperties cleaner) (.setOmitComments true) (.setPruneTags "script,style"))]
      (when-let [node (.clean cleaner html-str)]
        (-> (PrettyXmlSerializer. cleanprop)
          ;(log-debug node)
          (.getAsString node))))))