(ns fig.geom
  "Records and supporting function to represent and manipulate
  various geometry objects."
  (:require [schema.core :as s]))

(defrecord Point [x y shape])
(s/defn point :- Point
  [x :- s/Num
   y :- s/Num
   shape :- s/Keyword]
  (Point. x y shape))

(defrecord Rect [ul-x ul-y width height fill stroke stroke-width style])
(defrecord Line [x1 y1 x2 y2])
