(ns practitest-assignment.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::loading?
 (fn [db]
   (:loading? db)))

(re-frame/reg-sub
 ::error
 (fn [db]
   (:error db)))

(re-frame/reg-sub
 ::posts
 (fn [db]
   (:posts db)))

(re-frame/reg-sub
 ::expanded
 (fn [db]
   (:expanded db)))

(re-frame/reg-sub
 ::current-page
 (fn [db]
   (:current-page db)))

(def page-size 10)

(re-frame/reg-sub
 ::page-count
 :<- [::posts]
 (fn [posts _]
   (js/Math.ceil (/ (count posts) page-size))))

(re-frame/reg-sub
 ::paginated-posts
 :<- [::posts]
 :<- [::current-page]
 (fn [[posts page] _]
   (let [start (* (dec page) page-size)]
     (subvec (vec posts) start (min (+ start page-size) (count posts))))))
