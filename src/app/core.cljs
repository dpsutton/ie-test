(ns app.core
  (:require
   [reagent.core :as r]))

(defn schedule-heat! []
  (js/setTimeout (fn []
                   (let [heat (mapv (fn [x] (/ x 3.234234234234)) (range 1e5))])) 0))

(defn component [event state]
  [:input {event #(let [value (.. % -target -value)]
                    (reset! state value)
                    (schedule-heat!))
           :value @state
           :style {:width "600px"
                   :height "30px"}}])

(defn app []
  (let [change (r/atom "")
        input (r/atom "")]
    (fn []
      [:div
       [:pre (pr-str [:the-heat '(mapv (fn [x] (/ x 3.234234234234)) (range 1e5))])]
       [:h1 "on change"]
       [component :on-change change]
       [:h1 "on input"]
       [component :on-input input]])))

(defn ^:dev/after-load start
  []
  (r/render [app] (.getElementById js/document "app")))

(defn ^:export main [] (start))
