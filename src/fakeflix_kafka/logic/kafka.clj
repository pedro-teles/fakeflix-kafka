(ns fakeflix-kafka.logic.kafka)

(defn remove-nils
  [topics]
  (filter #(not (nil? %)) topics))

(defn valid-topics?
  [topics]
  (and (not (nil? topics)) (map? (not-empty topics))))

(defn topics-from-consumer
  [topics]
  (map :topic (vals topics)))

(defn topic-from-producer
  [topics topic]
  (topic topics))

(defn consumer-handler-fn
  [topic consumer-topics]
  (->> consumer-topics
       vals
       (filter #(= (:topic %) topic))
       first
       :handler-fn))

(defn merge-topics
  [consumer-topics producer-topics]
  (into (topics-from-consumer consumer-topics) (vals producer-topics)))
