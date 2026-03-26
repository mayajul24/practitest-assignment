(ns practitest-assignment.views
  (:require
   [re-frame.core :as re-frame]
   [practitest-assignment.subs :as subs]
   [practitest-assignment.events :as events]))

(defn post-card [post expanded?]
  [:div.post-card
   {:class    (when expanded? "expanded")
    :on-click #(re-frame/dispatch [::events/toggle-post (:id post)])}
   [:div.post-header
    [:span.post-arrow (if expanded? "▾" "▸")]
    [:h3.post-title (:title post)]]
   (when expanded?
     [:p.post-body (:body post)])])

(defn search-bar []
  (let [search @(re-frame/subscribe [::subs/search])]
    [:input.search-input
     {:type        "text"
      :placeholder "Search by title..."
      :value       search
      :on-change   #(re-frame/dispatch [::events/set-search (-> % .-target .-value)])}]))

(defn pagination []
  (let [current-page @(re-frame/subscribe [::subs/current-page])
        page-count   @(re-frame/subscribe [::subs/page-count])]
    [:div.pagination
     [:button {:disabled (= current-page 1)
               :on-click #(re-frame/dispatch [::events/set-page (dec current-page)])}
      "← Prev"]
     (for [p (range 1 (inc page-count))]
       ^{:key p}
       [:button {:class    (when (= p current-page) "active")
                 :on-click #(re-frame/dispatch [::events/set-page p])}
        p])
     [:button {:disabled (= current-page page-count)
               :on-click #(re-frame/dispatch [::events/set-page (inc current-page)])}
      "Next →"]]))

(defn post-list []
  (let [posts    @(re-frame/subscribe [::subs/paginated-posts])
        expanded @(re-frame/subscribe [::subs/expanded])]
    [:div.post-list
     (for [post posts]
       ^{:key (:id post)}
       [post-card post (contains? expanded (:id post))])]))

(defn main-panel []
  (let [loading? @(re-frame/subscribe [::subs/loading?])
        error    @(re-frame/subscribe [::subs/error])]
    [:<>
     [:header.navbar
      [:div.navbar-inner
       [:h1.app-title "Practitest Posts"]
       [search-bar]]]
     [:main.app
      (cond
        loading? [:div.loading [:div.spinner]]
        error    [:p.error (str "Error: " error)]
        :else    [:<>
                  [post-list]
                  [pagination]])]]))
