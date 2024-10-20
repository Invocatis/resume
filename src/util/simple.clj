(ns util.simple)


(defn simplify
  [data]
  (let [{:keys [personal-information education experience skills projects]} data
        {:keys [name surname city country contact]} personal-information
        {:keys [email linkedin github]} contact
        fullname (str name " " surname)]
    {fullname
     {:contact {:location (str city " | " country)
                :email email
                :LinkedIn linkedin
                :GitHub github}
      :education education}
     :experience (apply merge (mapv (fn [{:keys [company] :as experience}]
                                      {company (dissoc experience :company)})
                                    experience))
     :skills skills
     :projects projects}))