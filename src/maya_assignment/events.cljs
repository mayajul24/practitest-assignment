(ns maya-assignment.events
  (:require
   [re-frame.core :as re-frame]
   [maya-assignment.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))
