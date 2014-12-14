(ns fig.core
  (:require [fig.svg]
            [fig.display]))

(sm/defn background :- Element
  [width :- sc/Num
   height :- sc/Num
   bg-color :- Color
   major-grid-color :- Color
   major-grid-width :- sc/Num]
  (rect))
