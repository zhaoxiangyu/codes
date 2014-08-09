(ns org.sharpx.misc)

(def on-windows?
  (= "Windows_NT" (System/getenv "OS")))
