(ns app.core
  (:require
   [reagent.core :as r]))

(defn component [on-change value]
  [:input {:on-change on-change :value value
           :style {:width "600px"
                   :height "30px"}}])

(defn app []
  (let [state (r/atom "")]
    (fn []
      [component #(let [heat (mapv (fn [x] (/ x 3.234234234234)) (range 1e4))
                        value (.. % -target -value)]
                    (js/setTimeout (fn [] (reset! state value)) 0))
       @state])))

(defn ^:dev/after-load start
  []
  (r/render [app] (.getElementById js/document "app")))

(defn ^:export main [] (start))
