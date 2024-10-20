(ns util.markdown)

(def ^:dynamic should-tab? false)

(def LIST (Object.))

(defmulti hiccup->markdown
  (fn [x]
    (cond
     (vector? x) (first x)
     (seq? x) LIST
     :else :default)))

(defmethod hiccup->markdown LIST
  [content]
  (->> content (map hiccup->markdown) (interpose \space) (apply str)))

(defmethod hiccup->markdown :h1
  [[_ & text]]
  (apply str "# " (apply str (interpose \space text))))

(defmethod hiccup->markdown :h2
  [[_ & text]]
  (apply str "## " (apply str (interpose \space text))))

(defmethod hiccup->markdown :h3
  [[_ & text]]
  (apply str "### " (apply str (interpose \space text))))

(defmethod hiccup->markdown :h4
  [[_ & text]]
  (apply str "#### " (apply str (interpose \space text))))

(defmethod hiccup->markdown :h5
  [[_ & text]]
  (apply str "##### " (apply str (interpose \space text))))

(defmethod hiccup->markdown :body
  [[_ & content]]
  (->> content (map hiccup->markdown) (interpose "\n\n") (apply str)))

(defmethod hiccup->markdown :header
  [[_ & content]]
  (->> content (map hiccup->markdown) (interpose "\n\n") (apply str)))

(defmethod hiccup->markdown :div
  [[_ & content]]
  (->> content (map hiccup->markdown) (interpose "\n\n") (apply str)))

(defmethod hiccup->markdown :span
  [[_ & content]]
  (->> content (map hiccup->markdown) (interpose " ") (apply str)))

(defmethod hiccup->markdown :article
  [[_ & content]]
  (->> content (map hiccup->markdown) (interpose "\n\n") (apply str)))

(defmethod hiccup->markdown :hr
  [_]
  "---")

(defmethod hiccup->markdown :ul
  [[_ & content]]
  (->> content
       (map (fn [md] (binding [should-tab? true] (hiccup->markdown md))))
       (map (fn [md]
              (->> (clojure.string/split-lines md)
                   (map (fn [line] (if should-tab? (str \tab line) line)))
                   (interpose \newline)
                   (apply str))))
       (interpose \newline)
       (apply str)))

(defmethod hiccup->markdown :li
  [[_ & content]]
  (->> content (map hiccup->markdown) (interpose \space) (apply str "- ")))

(defmethod hiccup->markdown :br
  [_]
  "\n")

(defmethod hiccup->markdown :strong
  [[_ & text]]
  (str (apply str "**" text) "**"))

(defmethod hiccup->markdown :i
  [[_ & text]]
  (str (apply str "*" text) "*"))

(defmethod hiccup->markdown :em
  [[_ & text]]
  (str (apply str "**" text) "**"))

(defmethod hiccup->markdown :a
  [[_ {:keys [href]} text]]
  (str "[" (or text href) "](" href ")"))

(defmethod hiccup->markdown :default
  [any]
  any)