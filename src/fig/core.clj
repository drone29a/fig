(ns fig.core
  (:require [fig.svg :as svg]
            [fig.geom :as geom]
            [fig.display]
            [schema.core :as s])
  (:import [clojure.data.xml Element]
           [org.w3c.dom Document]))

(def Geom s/Any)
(def DataFrame s/Any)
(def Layer s/Any)

(defrecord Figure [title
                   theme
                   data
                   data-stats
                   plots
                   layers])

(s/defn figure :- Figure
  [title :- s/Str
   theme :- {s/Keyword s/Any}
   data :- {s/Keyword s/Any}
   data-stats :- {s/Keyword s/Any}
   plots :- [{s/Keyword s/Any}]
   layers :- [Element]]
  (Figure. title theme data data-stats plots layers))

(s/defn background :- Layer
  [width :- s/Num
   height :- s/Num
   bg-color :- svg/Color
   major-grid-color :- svg/Color
   major-grid-width :- s/Num]
  )

(s/defn scatter-plot :- Layer
  [data :- DataFrame
   x :- s/Keyword
   y :- s/Keyword
   shape :- s/Keyword]
  (map (fn [row] (geom/point (get row x) (get row y) shape))))

(s/defn render :- Document
  "Render a figure to an SVG document."
  [fig :- Figure]
  (-> (svg/svg )
      (svg/xml-doc)))

(s/defn example :- s/Any
  []

  ;;; Plot object is created and defines one coordinate system and one or more scales.
  ;;; Layers may be added to the plot object and each layer refers to it's own data and stats.
  ;;; Labels and bounds for the plot can be derived from the layers, how to best do this?
  
  (-> (plot "Example plot"
            default-theme
            cartesian-coords
            [scale-x-continuous scale-y-continuous scale-color-hue])
      (add-layer (scatter-plot data :x :y identity (jitter 0.5 2.0))))

  ;; These plot functions are building up a data structure which describe the figure to
  ;; generate. That data structure is then used to build an SVG document
  {:title "Example plot"
   :theme default-theme
   :layers [Layer] ; these are plot layers. background is a single layer, and then each addtl. plot
   }
  
  }

  ;; Compilation that happens from items in :plots to :layers:
  ;; plot -> [geometry] -> svg element
  
  )
