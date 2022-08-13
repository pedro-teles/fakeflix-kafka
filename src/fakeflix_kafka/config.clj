(ns fakeflix-kafka.config
  (:require [fakeflix-kafka.consumer :as consumer]
            [fakeflix-kafka.logic.kafka :as logic.kafka]
            [fakeflix-kafka.producer :as producer]
            [fakeflix-kafka.topics :as topics]))

(defn start-kafka
  ([group-id consumer-topics producer-topics]
   (start-kafka "localhost:9092" group-id consumer-topics producer-topics))
  ([bootstrap-server group-id consumer-topics producer-topics]
   (let []
     (consumer/build-consumer consumer-topics bootstrap-server group-id)
     (producer/build-producer producer-topics bootstrap-server)
     (topics/create-topics! (logic.kafka/merge-topics consumer-topics producer-topics) bootstrap-server 1 1)
     (topics/update-topics-reference consumer-topics producer-topics)
     (consumer/subscribe-topics (logic.kafka/topics-from-consumer consumer-topics))
     (consumer/observe-messages))))