(ns practitest-assignment.views
  (:require
   [re-frame.core :as re-frame]
   [practitest-assignment.subs :as subs]
   [practitest-assignment.events :as events]))

(defn post-card [post expanded?]
  [:div.post-card
   {:on-click #(re-frame/dispatch [::events/toggle-post (:id post)])}
   [:div.post-header
    [:span.post-arrow (if expanded? "▾" "▸")]
    [:h3.post-title (:title post)]]
   (when expanded?
     [:p.post-body (:body post)])])

(defn post-list []
  (let [posts    @(re-frame/subscribe [::subs/posts])
        expanded @(re-frame/subscribe [::subs/expanded])]
    [:div.post-list
     (for [post posts]
       ^{:key (:id post)}
       [post-card post (contains? expanded (:id post))])]))

(defn main-panel []
  (let [loading? @(re-frame/subscribe [::subs/loading?])
        error    @(re-frame/subscribe [::subs/error])]
    [:div.app
     [:h1.app-title "Posts"]
     (cond
       loading? [:p.loading "Loading posts..."]
       error    [:p.error (str "Error: " error)]
       :else    [post-list])]))
