#!/usr/bin/env bb

(load-file "src/util/markdown.clj")

(def resume-data (clojure.edn/read-string (slurp "resources/data.edn")))

(defn nested-list->ul
  [list]
  (into
   [:ul]
   (map (fn [li] (if (vector? li) (nested-list->ul li) [:li li])) list)))

(defn section|personal-info
  [{:keys [email location linkedin github]} {:keys [degree year institution]}]
  [:div
   [:span "Email: " email]
   [:span "Location: " location]
   [:span "LinkedIn: " linkedin]
   [:span "Github: " github]
   [:span "Education: " degree " | " institution " [" year "]"]])

(defn experience|info
  [roles duration company location]
  [:div
   (into
    [:div]
    (for [{:keys [title duration]} roles]
      [:h3 title duration]))
   [:span duration]
   [:span "Company: " [:strong company]]
   [:span "Location: " [:strong location]]])

(defn experience|responsibilities
  [responsibilities]
  [:div
   [:h5 "Responsibilities"]
   (nested-list->ul responsibilities)])

(defn experience|projects
  [projects]
  [:div
   [:h5 "Projects"]
   (nested-list->ul projects)])

(defn experience|skills
  [skills]
  (into
   [:span "Skills Used:"]
   (interpose \| skills)))

(defn element|experience
  [{:keys [roles duration location company details skills]}]
  [:div
   (experience|info roles duration location company)
   (experience|responsibilities (:responsibilities details))
   (experience|projects (:projects details))
   (experience|skills skills)])

(defn section|experience
  [experiences]
  (into
   [:article]
   (map element|experience experiences)))

(defn section|skills
  [skills]
  [:div
   [:h2 "Skills"]
   (into [:span] (interpose \| skills))])

(defn section|projects
  [projects]
  (into
   [:div
    [:h2 "Projects"]]
   (for [{:keys [name description link]} projects]
     [:div
      [:h4 name]
      [:i description]
      [:a {:href link}]])))

(defn generate-resume
  [{:keys [name contact education experience skills projects]}]
  (util.markdown/hiccup->markdown
   [:body
    [:header
     [:h1 name]
     [:hr]
     (section|personal-info contact education)]
    [:hr]
    (section|experience experience)
    (section|skills skills)
    (section|projects projects)]))



(println (generate-resume resume-data))
