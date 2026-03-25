(ns practitest-assignment.events
  (:require
   [re-frame.core :as re-frame]
   [practitest-assignment.db :as db]))

;; Custom effect: HTTP GET using js/fetch
(re-frame/reg-fx
 ::http-get
 (fn [{:keys [url on-success on-failure]}]
   (-> (js/fetch url)
       (.then (fn [resp]
                (if (.-ok resp)
                  (.json resp)
                  (throw (js/Error. (str "HTTP error " (.-status resp)))))))
       (.then (fn [data]
                (re-frame/dispatch [on-success (js->clj data :keywordize-keys true)])))
       (.catch (fn [err]
                 (re-frame/dispatch [on-failure (.-message err)]))))))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-fx
 ::fetch-posts
 (fn [{:keys [db]} _]
   {:db       (assoc db :loading? true :error nil)
    ::http-get {:url        "https://jsonplaceholder.typicode.com/posts"
                :on-success ::fetch-posts-success
                :on-failure ::fetch-posts-failure}}))

(re-frame/reg-event-db
 ::fetch-posts-success
 (fn [db [_ posts]]
   (assoc db :posts posts :loading? false)))

(re-frame/reg-event-db
 ::fetch-posts-failure
 (fn [db [_ error]]
   (assoc db :loading? false :error error)))

(re-frame/reg-event-db
 ::set-search
 (fn [db [_ query]]
   (assoc db :search query :current-page 1)))

(re-frame/reg-event-db
 ::set-page
 (fn [db [_ page]]
   (assoc db :current-page page)))

(re-frame/reg-event-db
 ::toggle-post
 (fn [db [_ id]]
   (update db :expanded
           (fn [expanded]
             (if (contains? expanded id)
               (disj expanded id)
               (conj expanded id))))))
