(ns fakeflix-kafka.topics
  (:import (org.apache.kafka.clients.admin NewTopic)
           (org.apache.kafka.clients.admin AdminClientConfig KafkaAdminClient NewTopic)))

(def producer (atom {}))

(def consumer (atom {}))

(defn create-topics!
  [topics bootstrap-server ^Integer partition ^Short replication]
  (let [config {AdminClientConfig/BOOTSTRAP_SERVERS_CONFIG bootstrap-server}
        admin-client (KafkaAdminClient/create config)
        new-topics (map (fn [^String topic-name]
                          (NewTopic. topic-name partition replication)) topics)]
    (.createTopics admin-client new-topics)))

(defn update-topics-reference
  [consumer-topics producer-topics]
  (swap! fakeflix-kafka.topics/consumer merge consumer-topics)
  (swap! fakeflix-kafka.topics/producer merge producer-topics))