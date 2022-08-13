(defproject org.clojars.pedroso/fakeflix-kafka "1.0.0"
  :description "Fakeflix Kafka library"
  :url "https://pedroteles.dev"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :plugins [[com.github.clojure-lsp/lein-clojure-lsp "1.3.9"]
            [org.clojure/clojure "1.10.3"]
            [org.apache.kafka/kafka-clients "3.1.0"]
            [org.apache.kafka/kafka_2.12 "3.1.0"]]
  :dependencies [[org.clojure/clojure "1.10.3"]]
  :aliases {"diagnostics"  ["clojure-lsp" "diagnostics"]
            "format"       ["clojure-lsp" "format" "--dry"]
            "format-fix"   ["clojure-lsp" "format"]
            "clean-ns"     ["clojure-lsp" "clean-ns" "--dry"]
            "clean-ns-fix" ["clojure-lsp" "clean-ns"]
            "lint"         ["do" ["diagnostics"] ["format"] ["clean-ns"]]
            "lint-fix"     ["do" ["format-fix"] ["clean-ns-fix"]]})
