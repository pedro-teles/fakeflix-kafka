(ns fakeflix-kafka.fixtures.topics)

(def producer-topics {:topic-1 "TOPIC-1"
                      :topic-2 "TOPIC-2"
                      :topic-3 "TOPIC-3"})

(def consumer-topics {:topic-4 {:topic "TOPIC-4"
                                :handler-fn test}
                      :topic-5 {:topic "TOPIC-5"
                                :handler-fn test}
                      :topic-6 {:topic "TOPIC-6"
                                :handler-fn test}})