(ns org.sharpx.parser.htmlclean-test
  (:use org.sharpx.parser.htmlclean)
  (:use org.sharpx.log)
  (:use [clojure test])
  )

(deftest test-clean
  (let [xml (html-clean "<html><head><title>test</title></head><body>test content</html>")
        ]
    (prn xml)
    (is (not (nil? xml)))
    )
  )

(deftest test-clean2
  (let [xml (html-clean "<html><body>test content</body></html>")
        ]
    (prn xml)
    (is (not (nil? xml)))
    )
  )
