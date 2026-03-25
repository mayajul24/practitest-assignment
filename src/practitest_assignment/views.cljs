(ns practitest-assignment.views
  (:require
   [re-frame.core :as re-frame]
   [practitest-assignment.subs :as subs]))

(defn post-card [post]
  [:div.post-card
   [:h3.post-title (:title post)]
   [:p.post-body (:body post)]])

(defn post-list []
  (let [posts @(re-frame/subscribe [::subs/posts])]
    [:div.post-list
     (for [post posts]
       ^{:key (:id post)}
       [post-card post])]))

(defn main-panel []
  (let [loading? @(re-frame/subscribe [::subs/loading?])
        error    @(re-frame/subscribe [::subs/error])]
    [:div.app
     [:h1.app-title "Posts"]
     (cond
       loading? [:p.loading "Loading posts..."]
       error    [:p.error (str "Error: " error)]
       :else    [post-list])]))
