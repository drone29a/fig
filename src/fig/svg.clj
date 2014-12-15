(ns fig.svg
  "Functions to generate SVG geometry used in plot figures."
  (:require [clojure.data.xml :refer [sexp-as-element
                                      element
                                      emit-str]]
            [clojure.string :refer [join]]
            [schema.core :as s])
  (:import [clojure.data.xml Element]
           [org.w3c.dom Document]
           [java.io ByteArrayInputStream InputStream]
           [java.nio.charset Charset]
           [org.apache.batik.dom.svg SAXSVGDocumentFactory]
           [org.apache.batik.util XMLResourceDescriptor]))

(def Color s/Str)
(def Props {s/Keyword (s/either s/Str s/Num)})


(s/defn string-input-stream :- InputStream
  [s :- s/Str]
  (ByteArrayInputStream. (-> (Charset/forName "ASCII")
                             (.encode s)
                             (.array))))

(s/defn xml-doc :- Document
  [root :- Element]
  (let [factory (SAXSVGDocumentFactory. (XMLResourceDescriptor/getXMLParserClassName))]
    (.createDocument factory
                     "foo"
                     (create-input-stream (emit-str root)))))

(s/defn svg :- Element
  [content :- [Element]]
  (element :svg {:xmlns "http://www.w3.org/2000/svg"
                 :xmlns:xlink "http://www.w3.org/1999/xlink"
                 :xmlns:svg "http://www.w3.org/2000/svg"}
           content))

(s/defn style :- s/Str
  [props :- Props]
  (->> props
       (map (fn [[k v]] (format "%s: %s" (name k) (str v))))
       (join ";")))

(s/defn rect :- Element
  [x :- s/Num
   y :- s/Num
   width :- s/Num
   height :- s/Num
   style-props :- Props]
  (element :rect {:x x
                  :y y
                  :width width
                  :height height
                  :style (style style-props)}))

(s/defn point :- Element
  [x :- s/Num
   y :- s/Num
   size :- s/Num
   shape :- s/Keyword]
  nil)
