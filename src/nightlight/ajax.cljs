(ns nightlight.ajax
  (:require [cljs.reader :refer [read-string]]
            [nightlight.state :as s]
            [nightlight.constants :as c])
  (:import goog.net.XhrIo))

(defn download-tree [cb]
  (.send XhrIo
    "tree"
    (fn [e]
      (when (.isSuccess (.-target e))
        (cb (read-string (.. e -target getResponseText)))))
    "GET"))

(defn download-state [cb]
  (.send XhrIo
    "read-state"
    (fn [e]
      (when (.isSuccess (.-target e))
        (reset! s/pref-state (read-string (.. e -target getResponseText))))
      (download-tree cb))
    "GET"))

(defn download-completions [info completions]
  (.send XhrIo
    "completions"
    (fn [e]
      (reset! completions (read-string (.. e -target getResponseText))))
    "POST"
    (pr-str info)))

(defn write-file [editor]
  (when-not (-> @s/runtime-state :options :read-only?)
    (.send XhrIo
      "write-file"
      (fn [e]
        (c/mark-clean editor))
      "POST"
      (pr-str {:path (c/get-path editor) :content (c/get-content editor)}))))

(defn rename-file [from to cb]
  (when-not (-> @s/runtime-state :options :read-only?)
    (.send XhrIo "rename-file" cb "POST" (pr-str {:from from :to to}))))

(defn delete-file [path cb]
  (when-not (-> @s/runtime-state :options :read-only?)
    (.send XhrIo "delete-file" cb "POST" path)))

(defn new-file [path cb]
  (when-not (-> @s/runtime-state :options :read-only?)
    (.send XhrIo "new-file" cb "POST" path)))

(defn new-file-upload [form cb]
  (when-not (-> @s/runtime-state :options :read-only?)
    (let [form-data (js/FormData.)]
      (doseq [file (array-seq (.-files form))]
        (.append form-data "files" file (.-name file)))
      (.send XhrIo "new-file-upload" cb "POST" form-data))))

