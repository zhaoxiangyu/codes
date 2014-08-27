(ns org.sharpx.ds-util)

(defn peel
  "convert collection like [:a :b :c :d :e :f] to [:a :f :b :e :c :d]"
  [c f]
  (loop [fs (cycle [first last]) fs2 (cycle [rest drop-last])
         col c result []]
    (if (empty? col)
      result
      (do (when-not (nil? f) (f ((first fs) col)))
        (recur (rest fs) (rest fs2)
          ((first fs2) col) (conj result ((first fs) col)))))))