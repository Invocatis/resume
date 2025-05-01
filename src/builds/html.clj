#!/usr/bin/env bb

(def resume-data (clojure.edn/read-string (slurp "resources/data.edn")))

(defn nested-list->ul
  [list]
  (into
   [:ul]
   (map (fn [li] (if (vector? li) (nested-list->ul li) [:li li])) list)))

(defn keyword->humanized
  [keyword]
  (->>
   (-> keyword name (clojure.string/split #"-"))
   (map clojure.string/capitalize)
   (interpose \space)
   (apply str)))

(defn section|personal-info
  [{:keys [country city]} {:keys [email linkedin github]} {:keys [field-of-study degree year institution]}]
  [:div.section.info
   [:div#email {:onClick "textToClipboard(event)"} [:i.fa.fa-at] email]
   [:div#country [:i.fa.fa-map-marker] (str city " | " country)]
   [:div#linkedin [:i.fa.fa-brands.fa-linkedin-in] [:a {:href linkedin} linkedin]]
   [:div#github [:i.fa.fa-brands.fa-github] [:a {:href github} github]]
   [:div#degree [:i.fa.fa-graduation-cap] degree "[" field-of-study "]" " - " year]
   [:div#university [:i.fa.fa-university] institution]])

(defn experience|info
  [roles start-date end-date company location]
  [:div.info
   (into
    [:span]
    (for [{:keys [title duration]} roles]
      [:div [:strong title (when duration (str " (" duration ")"))]]))
   [:span.date [:i.fa.fa-calendar] (str start-date " - " (or end-date "Present"))]
   [:h4 company]
   [:span.location [:i.fa.fa-map-marker] [:strong location]]])

(defn experience|responsibilities
  [responsibilities]
  [:span
   [:h5 "Responsibilities"]
   (nested-list->ul responsibilities)])

(defn experience|projects
  [projects]
  (when projects
    [:span
     [:h5 "Projects"]
     (nested-list->ul projects)]))

(defn section|skills
  [skill-groups]
  [:div.section.skills
   [:h2 "Skills"]
   (into
    [:div.skill-groups]
    (for [[group-name group] skill-groups]
      [:div.skill-group
       [:h4 (keyword->humanized group-name)]
       [:div.skill-members (map (fn [skill] [:div.bubble skill]) group)]]))])

(defn element|experience
  [{:keys [roles start-date end-date location company responsibilities projects]}]
  [:div.experience
   (experience|info roles start-date end-date company location)
   [:div.details
    (experience|responsibilities responsibilities)
    (experience|projects projects)]])

(defn section|experience
  [experiences]
  (into
   [:div.section.experiences]
   (map element|experience experiences)))

(defn section|projects
  [projects]
  [:div.section.projects
   [:h2 "Projects"]
   [:div.projects
    (for [project projects]
      [:div.project
       [:h4 (:name project)]
       [:i (:description project)]
       [:br]
       [:a {:href (:link project)} (:link project)]])]])

(defn generate-resume
  [{:keys [personal-information education experience skills projects]}]
  (let [{:keys [name surname contact]} personal-information
        fullname (str name " " surname)]
    (hiccup.core/html
     [:html
      [:head
       [:meta {:charset "UTF-8"}]
       [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
       [:title fullname]
       [:style (hiccup2.core/raw (clojure.string/replace (slurp "resources/style.css") #"\n" ""))]
       [:link {:rel "stylesheet"
               :href "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
               :integrity "sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
               :crossorigin "anonymous"
               :referrerpolicy "no-referrer"}]]
      [:script
       "function textToClipboard(ev) {
          navigator.clipboard.writeText(ev.target.textContent)
        }"]
      [:body
       [:header
        [:h1 fullname]
        (section|personal-info personal-information contact education)]
       (section|experience experience)
       (section|skills skills)
       (section|projects projects)]])))

(println (generate-resume resume-data))
