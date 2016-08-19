(ns mailgun-dummy.server
  (:require [mailgun-dummy.core :refer [handler]]
            [mount.core :refer [defstate]]
            [ring.adapter.jetty :as jetty]))

(defn start []
  (jetty/run-jetty #'handler {:port 5151 :join? false}))

(defstate server
  :start (start)
  :stop (.stop server))

