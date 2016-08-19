(defproject mailgun-dummy "0.1.0-SNAPSHOT"
  :description "A mock of Mailgun's email service."
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.4.0"]
                 [compojure "1.4.0"]
                 [org.clojure/data.xml "0.0.8"]
                 [org.clojure/data.zip "0.1.1"]
                 [mount "0.1.9"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [org.clojure/core.async "0.2.374"]
                 [clj-time "0.11.0"]
                 [clj-http "2.0.1"]
                 [cheshire "5.5.0"]]
  :main mailgun-dummy.main)
