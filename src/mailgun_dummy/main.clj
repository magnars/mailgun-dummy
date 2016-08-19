(ns mailgun-dummy.main
  (:gen-class)
  (:require [mailgun-dummy.server]
            [mount.core :as mount]))

(defn -main []
  (mount/start))

(Thread/setDefaultUncaughtExceptionHandler
 (reify Thread$UncaughtExceptionHandler
   (uncaughtException [_ thread ex]
     (println "Uncaught exception on" (.getName thread))
     (.printStackTrace ex))))
