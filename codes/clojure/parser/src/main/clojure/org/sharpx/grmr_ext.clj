(ns org.sharpx.grmr-ext
  (:use [org.sharpx ho-util]))

(defn exception-cause?
  [klass ^Throwable t]
  (->> (iterate #(.getCause ^Throwable %) t)
    (take-while identity)
    (some (partial instance? klass))
    boolean))

(defmacro try-cause
  [& body]
  (let [checker (fn [form]
                  (or (not (sequential? form))
                    (not= 'catch (first form))))
        [code guards] (split-with checker body)
        error-local (gensym "t")
        guards (forcat [[_ klass local & guard-body] guards]
                 `((exception-cause? ~klass ~error-local)
                    (let [~local ~error-local]
                      ~@guard-body
                      )))]
    `(try ~@code
       (catch Throwable ~error-local
         (cond ~@guards
           true (throw ~error-local)
           )))))