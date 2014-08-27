(ns org.sharpx.ds-util)

(defn peel
  "convert collection like [:a :b :c :d :e :f] to [:a :f :b :e :c :d]"
  [c]
  (loop [f (cycle [first last]) f2 (cycle [rest drop-last])
         col c result []]
    (when-not (empty? col)
      ;(println ((first f) col))
      (recur (rest f) (rest f2)
        ((first f2) col) (conj result ((first f) col))))))