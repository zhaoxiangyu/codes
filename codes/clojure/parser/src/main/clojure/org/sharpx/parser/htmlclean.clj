(ns org.sharpx.parser.htmlclean
  (:use [org.sharpx log])
  (:import [org.htmlcleaner HtmlCleaner PrettyXmlSerializer CleanerProperties TagNode]
           [org.htmlcleaner.conditional ITagNodeCondition]
           [org.htmlcleaner.audit HtmlModificationListener ErrorType]))

(def modification-listener
  (reify org.htmlcleaner.audit.HtmlModificationListener
    (^void fireConditionModification [this ^ITagNodeCondition condition ^TagNode tagNode]
      (println "fireConditionModification"))
    (^void fireHtmlError [this ^boolean certain ^TagNode tagNode ^ErrorType errorType]
      (println "fireHtmlError"))
    (^void fireUglyHtml [this ^boolean certainty ^TagNode tagNode ^ErrorType errorType]
      (println "fireUglyHtml"))
    (^void fireUserDefinedModification [this ^boolean certainty ^TagNode tagNode ^ErrorType errorType]
      (println "fireUserDefinedModification"))))

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