(ns fig.svg
  "Functions to generate SVG geometry used in plot figures."
  (:require [clojure.data.xml :refer [sexp-as-element
                                      element
                                      emit-str]]
            [clojure.string :refer [join]]
            [schema.core :as sc]
            [schema.macros :as sm])
  (:import [clojure.data.xml Element]
           [org.w3c.dom Document]
           [java.io ByteArrayInputStream InputStream]
           [java.nio.charset Charset]
           [org.apache.batik.dom.svg SAXSVGDocumentFactory]
           [org.apache.batik.util XMLResourceDescriptor]))

(def Color sc/Str)
(def Props {sc/Keyword (sc/either sc/Str sc/Num)})


(sm/defn string-input-stream :- InputStream
  [s :- sc/Str]
  (ByteArrayInputStream. (-> (Charset/forName "ASCII")
                             (.encode s)
                             (.array))))

(sm/defn xml-doc :- Document
  [root :- Element]
  (let [factory (SAXSVGDocumentFactory. (XMLResourceDescriptor/getXMLParserClassName))]
    (.createDocument factory
                     "foo"
                     (create-input-stream (emit-str root)))))

(sm/defn svg :- Element
  [content :- [Element]]
  (element :svg {:xmlns "http://www.w3.org/2000/svg"
                 :xmlns:xlink "http://www.w3.org/1999/xlink"
                 :xmlns:svg "http://www.w3.org/2000/svg"}
           content))

(sm/defn style :- sc/Str
  [props :- Props]
  (->> props
       (map (fn [[k v]] (format "%s: %s" (name k) (str v))))
       (join ";")))

(sm/defn rect :- Element
  [x :- sc/Num
   y :- sc/Num
   width :- sc/Num
   height :- sc/Num
   style-props :- Props]
  (element :rect {:x x
                  :y y
                  :width width
                  :height height
                  :style (style style-props)}))
