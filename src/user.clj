(ns user
  (:require [clojure.tools.namespace.repl :as repl]
            [mailgun-dummy.server :as server]
            [mount.core :as mount]))

(defn start []
  (mount/start))

(defn stop []
  (mount/stop))

(defn reset []
  (stop)
  (repl/refresh :after 'user/start))
