(ns mailgun-dummy.core
  (:require [cheshire.core :as json]
            [clojure.core.async :refer [put!]]
            [clojure.walk :as walk]
            [mailgun-dummy.sender :refer [sender]]
            ring.middleware.multipart-params
            ring.middleware.params)
  (:import java.util.UUID))

(defn create-response [message]
  {:status 200
   :body (json/generate-string
          {:message "Queued. Thank you.",
           :id (:id message)})
   :headers {"content-type" "application/json"}})

(defn generate-id [message]
  (str "<" (.toString (java.util.UUID/randomUUID)) "--" (:to message) ">"))

(defn valid-email? [s]
  (or (re-find #"^[^@<>]+@[^@<>]+\.[^@<>]+$" s)
      (re-find #"^[^<]+ <[^@<>]+@[^@<>]+\.[^@<>]+>$" s)))

(defn valid-message? [{:keys [from to subject text html]}]
  (and (string? from) (valid-email? from)
       (string? to) (valid-email? to)
       (string? subject)
       (or (string? text)
           (string? html))))

(defn send-email [message]
  (let [message (walk/keywordize-keys message)
        message (assoc message :id (generate-id message))]
    (if (valid-message? message)
      (do
        (println "Received message" (pr-str (dissoc message :id)))
        (put! sender message)
        (create-response message))
      (do
        (println "Recevied invalid message" (pr-str (dissoc message :id)))
        {:status 400 :body "Invalid message!"}))))

(def handler
  (-> (fn [req]
        (if (= (get-in req [:headers "authorization"])
               "Basic YXBpOm1haWxndW4tZHVtbXkta2V5") ;; :basic-auth ["api" "mailgun-dummy-key"]
          (send-email (:params req))
          (do
            (println "Unauthorized request, set :mailgun-api-key to \"mailgun-dummy-key\"")
            {:status 401 :body "Unauthorized"})))
      (ring.middleware.multipart-params/wrap-multipart-params)
      (ring.middleware.params/wrap-params)))
