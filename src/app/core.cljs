(ns app.core
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]))

(rf/reg-sub
  :get
  (fn [db [_ k]]
    (get db k)))

(rf/reg-sub
  :all
  (fn [db _]
    db))

(defn big-computation
  [k v]
  (let [sums (fn [x] (reduce + 0 (range x)))]
    (mapv sums (range 4000)))
  (prn "computation finished: " k ": " v))

(rf/reg-event-db
  :assoc
  [rf/trim-v]
  (fn [db [k v]]
    (big-computation k v)
    (assoc db k v)))

(defn component [event]
  (let [value (or @(rf/subscribe [:get event]) "")]
    [:input {event #(let [value (.. % -target -value)]
                      (rf/dispatch-sync [:assoc event value]))
             :value value
             :style {:width "600px"
                     :height "30px"}}]))

(defn app []
  (let [all @(rf/subscribe [:all])]
    [:div
     [:h1 "Reframe map:"]
     [:pre (pr-str all)]
     [:pre (pr-str [:the-heat '(mapv (fn [x] (/ x 3.234234234234)) (range 1e5))])]
     [:h1 "on change"]
     [component :on-change]
     [:h1 "on input"]
     [component :on-input]]))

(defn ^:dev/after-load start
  []
  (r/render [app] (.getElementById js/document "app")))

(rf/reg-event-db
  :initialize-db
  (fn [_ _]
    {:on-change "initial value"
     :on-input "initial value"}))

(defn ^:export main
  []
  (rf/dispatch [:initialize-db])
  (start))
