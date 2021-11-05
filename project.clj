(defproject mailgun-dummy "0.1.0-SNAPSHOT"
  :description "A mock of Mailgun's email service."
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [ring "1.9.4"]
                 [compojure "1.6.2"]
                 [org.clojure/data.xml "0.0.8"]
                 [org.clojure/data.zip "1.0.0"]
                 [mount "0.1.16"]
                 [org.clojure/tools.namespace "1.1.0"]
                 [org.clojure/core.async "1.4.627"]
                 [clj-time "0.15.2"]
                 [clj-http "3.12.3"]
                 [cheshire "5.10.1"]]
  :main mailgun-dummy.main)
