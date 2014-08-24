(ns org.sharpx.ho-util)

(defmacro forcat
  [[args aseq] & body]
  `(mapcat (fn [~args]
             ~@body)
     ~aseq))