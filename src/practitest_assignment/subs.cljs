(ns practitest-assignment.subs
  (:require
   [re-frame.core :as re-frame]
   [clojure.string :as str]))

;; Raw subscriptions — direct db lookups
(re-frame/reg-sub ::loading?     (fn [db] (:loading? db)))
(re-frame/reg-sub ::error        (fn [db] (:error db)))
(re-frame/reg-sub ::posts        (fn [db] (:posts db)))
(re-frame/reg-sub ::expanded     (fn [db] (:expanded db)))
(re-frame/reg-sub ::current-page (fn [db] (:current-page db)))
(re-frame/reg-sub ::search       (fn [db] (:search db)))

;; Derived subscriptions
(def page-size 10)

(re-frame/reg-sub
 ::filtered-posts
 :<- [::posts]
 :<- [::search]
 (fn [[posts search] _]
   (if (empty? search)
     posts
     (filter #(str/includes?
               (str/lower-case (:title %))
               (str/lower-case search))
             posts))))

(re-frame/reg-sub
 ::page-count
 :<- [::filtered-posts]
 (fn [posts _]
   (js/Math.ceil (/ (count posts) page-size))))

(re-frame/reg-sub
 ::paginated-posts
 :<- [::filtered-posts]
 :<- [::current-page]
 (fn [[posts page] _]
   (let [start (* (dec page) page-size)]
     (subvec (vec posts) start (min (+ start page-size) (count posts))))))