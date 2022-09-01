(ns fakeflix-kafka.consumer
  (:require [fakeflix-kafka.log :as log]
            [fakeflix-kafka.logic.kafka :as logic.kafka]
            [fakeflix-kafka.topics :as topics])
  (:import (java.time Duration)
           (org.apache.kafka.clients.consumer KafkaConsumer)
           (org.apache.kafka.common.serialization StringDeserializer)))

(def consumer (atom nil))

(defn create-consumer
  [bootstrap-server group-id]
  (let [consumer-props
        {"bootstrap.servers",  bootstrap-server
         "group.id",           group-id
         "key.deserializer",   StringDeserializer
         "value.deserializer", StringDeserializer
         "auto.offset.reset",  "latest"
         "enable.auto.commit", "true"}]
    (swap! consumer (fn [_]
                      (KafkaConsumer. consumer-props)))))

(defn build-consumer ^KafkaConsumer
  [topics bootstrap-server group-id]
  (if (logic.kafka/valid-topics? topics)
    (create-consumer bootstrap-server group-id)))

(defn subscribe-topics
  [topics]
  (if-not (and (nil? @consumer) (empty? topics))
    (.subscribe @consumer topics)))

(defn fetch-messages
  []
  (while true
    (let [records (.poll @consumer (Duration/ofMillis 1000))]
      (doseq [record records]
        (let [topic (.topic record)
              handler-fn (logic.kafka/consumer-handler-fn topic @topics/consumer)]
          (try
            (handler-fn (.value record))
            (log/info (str "Message consumed from topic: " topic))
            (catch Exception e (log/error e (str "Error consuming message from topic " topic))))))
      (.commitAsync @consumer))))

(defn consume-messages
  []
  (if-not (nil? @consumer)
    (future (fetch-messages))))
