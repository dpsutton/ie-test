(ns app.core
  (:require
   [reagent.core :as r]))

(defn component [on-change value]
  [:input {:on-change on-change :value value}])

(defn app []
  (let [state (r/atom "")]
    (fn []
      [component #(let [value (.. % -target -value)]
                    (reset! state value))
       @state])))

(defn ^:dev/after-load start
  []
  (r/render [app] (.getElementById js/document "app")))

(defn ^:export main [] (start))
