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
  [{:keys [country city]} {:keys [email linkedin github]}]
  [:div.section.info
   [:h2.contact [:i.fa.fa-address-book] "Contact"]
   [:hr.minor]
   [:div#email {:onClick "textToClipboard(event)"} [:i.fa.fa-at] email]
   [:div#location [:i.fa.fa-map-marker] (str city #_" | " #_country)]
   [:div#linkedin [:i.fa.fa-brands.fa-linkedin-in] [:a {:href linkedin} linkedin]]
   [:div#github [:i.fa.fa-brands.fa-github] [:a {:href github} github]]])

(defn section|synopsis
  [points]
  #_(->> points
       (map (fn [x] [:span x]))
       (into
        [:div.section.synopsis])))

(defn section|education
  [{:keys [field-of-study degree year institution]}]
  [:div.section.education
    [:h2 "Education"]
    [:div#degree [:i.fa.fa-graduation-cap] degree " -- " field-of-study]
    [:div#university [:i.fa.fa-university] institution]
    [:div#date [:i.fa.fa-calendar] year]])

(defn experience|info
  [roles start-date end-date company icon-url location]
  [:div.info
   [:div.company-composite
    [:img {:src icon-url}]
    [:h3.company company]]
   [:span.location [:i.fa.fa-map-marker] [:strong location]]
   (into
    [:span]
    (for [{:keys [title duration]} roles]
      [:div [:strong title (when duration (str " (" duration ")"))]]))
   [:span.date [:i.fa.fa-calendar] (str start-date " - " (or end-date "Present"))]])

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
       (->> group
            (interpose ",")
            (partition 2 2 nil)
            (map (fn [pair] (apply str pair)))
            (map (fn [skill] [:div.bubble skill]))
            (into [:div.skill-members]))]))])

(defn element|experience
  [{:keys [roles start-date end-date location company icon-url responsibilities projects]}]
  [:div.experience
   (experience|info roles start-date end-date company icon-url location)
   [:div.details
    (experience|responsibilities responsibilities)
    (experience|projects projects)]])

(defn section|experience
  [experiences]
  (->> experiences
       (map element|experience)
       (interpose [:hr.minor])
       (into [:div.experiences])))

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
  (let [{:keys [name surname tagline contact synopsis]} personal-information
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
       [:div.personal-info
        (section|personal-info personal-information contact)
        (section|synopsis synopsis)
        (section|education education)
        (section|skills skills)]
       [:div.career-info
        [:div.title
         [:h1.name fullname]
         [:h2.tagline tagline]]
        (conj
          (section|experience experience)
          (section|projects projects))]]])))

(println (generate-resume resume-data))
