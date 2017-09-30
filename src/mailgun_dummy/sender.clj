(ns mailgun-dummy.sender
  (:require [clj-http.client :as http]
            [clojure.core.async :refer [<! chan close! go go-loop timeout]]
            [mount.core :refer [defstate]]))

(def error-types ["failed"
                  "rejected"
                  "dropped"
                  "bounced"])

(defn create-confirmation-response [message]
  {:form-params {:Message-Id (:id message)
                 :event (if (or (.startsWith (:to message) "fail@")
                                (and (= (:text message) "fail?")
                                     (< (rand-int 100) 33)))
                          (rand-nth error-types)
                          "delivered")}})

(defn fire-away-delivery-confirmation [message]
  (let [ms (inc (rand-int 10000))]
    (print "Sending delivery report for" (:id message) "in" ms "ms ...")
    (flush)
    (go
      (<! (timeout ms))
      (http/post "http://localhost:3000/mailgun-callback"
                 (create-confirmation-response message))
      (println " done!"))))

(defn create-sender []
  (let [c (chan)]
    (println "Started listening for messages")
    (go-loop []
      (if-let [message (<! c)]
        (do (fire-away-delivery-confirmation message)
            (recur))
        (println "Stopped listening for messages")))
    c))

(defstate sender
  :start (create-sender)
  :stop (close! sender))
