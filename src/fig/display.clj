(ns fig.display
  "A Swing-based display for rendering SVG figures."
  (:require [schema.core :as sc]
            [schema.macros :as sm])
  (:import [javax.swing JFrame JPanel]
           [java.awt BorderLayout]
           [org.apache.batik.swing JSVGCanvas]))

(sm/defn create-frame
  [title :- sc/Str
   width :- sc/Int
   height :- sc/Int]
  (let [canvas (JSVGCanvas.)
        panel (doto (JPanel. (BorderLayout.))
                (.add canvas BorderLayout/CENTER))]
    {:frame (doto (JFrame. title)
              (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)
              (-> (.getContentPane)
                  (.add panel))
              (.setSize width height)
              (.pack)
              (.setVisible true))
     :canvas canvas}))
