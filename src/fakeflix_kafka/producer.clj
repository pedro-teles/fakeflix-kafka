(ns fakeflix-kafka.producer
  (:require [fakeflix-kafka.logic.kafka :as logic]
            [fakeflix-kafka.topics :as topics]
            [fakeflix-logs.observability :as observability])
  (:import (java.util UUID)
           (org.apache.kafka.clients.producer KafkaProducer ProducerRecord)
           (org.apache.kafka.common.serialization StringSerializer)))

(def producer (atom nil))

(defn create-producer ^KafkaProducer
  [bootstrap-server]
  (let [producer-props {"bootstrap.servers" bootstrap-server
                        "value.serializer"  StringSerializer
                        "key.serializer"    StringSerializer}]
    (KafkaProducer. producer-props)))

(defn build-producer
  [topics bootstrap-server]
  (if (logic/valid-topics? topics)
    (swap! producer (fn [_]
                      (create-producer bootstrap-server)))))

(defn produce!
  [message topic]
  (let [topic-name (logic/topic-from-producer @topics/producer topic)
        message-key (UUID/randomUUID)]
    (try
      (.send @producer (ProducerRecord. topic-name (str message-key) message))
      (observability/info (str "Message produced to topic: " topic-name))
      (catch Exception e
        (observability/error e (str "Error producing message to topic: " topic-name))))))
