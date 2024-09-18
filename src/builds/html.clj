#!/usr/bin/env bb

(def resume-data (clojure.edn/read-string (slurp "data.edn")))

(defn nested-list->ul
  [list]
  (into
   [:ul]
   (map (fn [li] (if (seq? li) (nested-list->ul li) [:li li])) list)))

(defn section|personal-info
  [{:keys [email location linkedin github]} {:keys [degree year institution]}]
  [:div.info
   [:div [:i.fa.fa-at] email]
   [:div [:i.fa.fa-map-marker] location]
   [:div [:i.fa.fa-brands.fa-linkedin-in] [:a {:href linkedin} linkedin]]
   [:div [:i.fa.fa-brands.fa-github] [:a {:href github} github]]
   [:div [:i.fa.fa-graduation-cap] degree " - " year]
   [:div [:i.fa.fa-university] institution]])

(defn experience|info
  [roles duration company location]
  [:div.info
   (into
    [:span]
    (for [{:keys [title duration]} roles]
      [:div [:strong title (when duration (str " (" duration ")"))]]))
   [:span.date [:i.fa.fa-calendar] duration]
   [:h4 company]
   [:span.location [:i.fa.fa-map-marker] [:strong location]]])

(defn experience|responsibilities
  [responsibilities]
  [:span
   [:h5 "Responsibilities"]
   (nested-list->ul responsibilities)])

(defn experience|projects
  [projects]
  [:span
   [:h5 "Projects"]
   (->>
    projects
    (map (fn [{:keys [name details]}]
           [[:li name]
            (nested-list->ul details)]))
    (reduce into [:ul]))])

(defn experience|skills
  [skills]
  (into
   [:div.skills]
   (map (fn [skill] [:div.bubble skill]) skills)))

(defn element|experience
  [{:keys [roles duration location company details skills]}]
  [:div.experience
   (experience|info roles duration company location)
   (experience|responsibilities (:responsibilities details))
   (experience|projects (:projects details))
   (experience|skills skills)])

(defn section|experience
  [experiences]
  (into
   [:article.experiences]
   (map element|experience experiences)))

(defn section|projects
  [projects]
  [:div
   [:h2 "Projects"]
   [:div.projects
    (for [project projects]
      [:div.project
       [:h4 (:name project)]
       [:i (:description project)]
       [:br]
       [:a {:href (:link project)} (:link project)]])]])

(defn generate-resume
  [{:keys [name contact education experience skills projects]}]
  (hiccup.core/html
   [:html
    [:head
     [:meta {:charset "UTF-8"}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
     [:title name]
     [:style (hiccup2.core/raw (clojure.string/replace (slurp "resources/style.css") #"\n" ""))]
     [:link {:rel "stylesheet"
             :href "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
             :integrity "sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
             :crossorigin "anonymous"
             :referrerpolicy "no-referrer"}]]
    [:body
     [:header
      [:h1 name]
      [:hr.minor]
      (section|personal-info contact education)]
     [:hr.major]
     (section|experience experience)
     (section|projects projects)]]))

(println (generate-resume resume-data))
