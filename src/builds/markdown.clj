#!/usr/bin/env bb

(load-file "src/util/markdown.clj")
(load-file "src/util/simple.clj")

(def resume-data (clojure.edn/read-string (slurp "resources/data.edn")))

;; (def extra-data (clojure.edn/read-string (slurp (first *command-line-args*))))

(defn capitalize
  [string]
  (str (clojure.string/capitalize (subs string 0 1)) (subs string 1)))

(defn pretty
  [string]
  (let [s (if (keyword? string) (name string) (str string))]
    (->>
     (clojure.string/split s #"-")
     (map capitalize)
     (interpose \space)
     (apply str))))

(declare -render)

(defn h
  [level]
  (keyword (str "h" level)))

(defn render|map
  [{:keys [level] :as context} data]
  (into
   [:div]
   (map
    (fn [[k v]]
      (let [rendered-value (-render (update context :level inc) v)]
        (if (string? rendered-value)
          [:span
           [:em (pretty k) ":"]
           rendered-value]
          [:div
           [(h level) (pretty k)]
           rendered-value])))
    data)))

(defn render|vector
  [context data]
  (let [rendered-children (mapv #(-render context %) data)]
    (cond
      #_(every? #(and (string? %) (< (count %) 16)) rendered-children)
      #_(into [:span] rendered-children)

      (every? string? data)
      (into [:ul] (map (fn [c] [:li c]) rendered-children))

      (and (every? map? data) (< (:level context) 3))
      (into [:div] (interpose [:hr] (map (fn [c] [:li c]) rendered-children)))

      (every? map? data)
      (into [:div] (interpose [:hr] (map (fn [c] [:li c]) rendered-children)))

      :else
      (into [:div] rendered-children))))

(defn render|string
  [context data]
  (str data))

(defn -render
  [context data]
  (cond
    (map? data)
    (render|map context data)

    (vector? data)
    (render|vector context data)

    :else
    (render|string context data)))

(defn render
  [data]
  [:div
   [:span [:i "Pretty Version:"] [:a {:href "https://invocatis.github.io/resume/target/resume.html"}]]
   (-render {:level 1} data)])

(println (util.markdown/hiccup->markdown (render (util.simple/simplify resume-data))))
