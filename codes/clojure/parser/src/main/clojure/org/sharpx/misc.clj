(ns org.sharpx.misc)

(def on-windows?
  (= "Windows_NT" (System/getenv "OS")))

(defn confirm
  "show message,if confirmed return yes-val else return no-val"
  [message yes-val no-val]
  (println message "y/n?")
  (let [input (read-line)]
    (if (or (= input "y") (= input "Y"))
      yes-val
      no-val)))

(defmacro mconfirm
  "show message,if confirmed return yes-expr else return no-expr"
  [message yes-expr no-expr]
  `(do
    (println ~message "y/n?")
    (let [~'input (read-line)]
      (if (or (= ~'input "y") (= ~'input "Y"))
        ~yes-expr
        ~no-expr))))
