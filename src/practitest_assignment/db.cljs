(ns practitest-assignment.db)

(def default-db
  {:posts    []
   :loading? false
   :error        nil
   :expanded     #{}
   :current-page 1})
