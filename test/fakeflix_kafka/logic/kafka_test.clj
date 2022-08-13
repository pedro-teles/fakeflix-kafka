(ns fakeflix-kafka.logic.kafka-test
  (:require [clojure.test :refer [deftest is testing]]
            [fakeflix-kafka.fixtures.topics :as fixtures.topics]
            [fakeflix-kafka.logic.kafka :as logic]))

(deftest remove-nils-test
  (testing "Should return a list without nils"
    (is (= (list "TOPIC-1" "TOPIC-2" "TOPIC-3")
           (logic/remove-nils (list "TOPIC-1" nil "TOPIC-2" "TOPIC-3" nil))))

    (is (= (list "TOPIC-1" "TOPIC-2" "TOPIC-3")
           (logic/remove-nils (list "TOPIC-1" "TOPIC-2" "TOPIC-3"))))))

(deftest valid-topics?-test
  (testing "Should return true when a valid map of topics are passed"
    (is (true? (logic/valid-topics? fixtures.topics/producer-topics)))

    (is (true? (logic/valid-topics? fixtures.topics/consumer-topics))))

  (testing "Should return false when a invalid map of topics are passed"
    (is (false? (logic/valid-topics? nil)))

    (is (false? (logic/valid-topics? {})))))

(deftest topics-from-consumer-test
  (testing "Should return a list of topics from a consumer"
    (is (= (list "TOPIC-4" "TOPIC-5" "TOPIC-6")
           (logic/topics-from-consumer fixtures.topics/consumer-topics)))))

(deftest topic-from-producer-test
  (testing "Should return a map with topic and handler-fn from a producer given producer keyword"
    (is (= "TOPIC-1"
           (logic/topic-from-producer fixtures.topics/producer-topics :topic-1)))))

(deftest consumer-handler-fn-test
  (testing "Should return the handler-fn from a given consumer topic"
    (is (fn? (logic/consumer-handler-fn "TOPIC-6" fixtures.topics/consumer-topics)))))

(deftest merge-topics-test
  (testing "Should return a merged list with both consumer and producer topics"
    (is (= 6
           (count (logic/merge-topics fixtures.topics/consumer-topics fixtures.topics/producer-topics))))))