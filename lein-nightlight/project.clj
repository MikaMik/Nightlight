(defproject nightlight/lein-nightlight "2.3.3-SNAPSHOT"
  :description "A conveninent Nightlight launcher for Leiningen projects"
  :url "https://github.com/oakes/Nightlight"
  :license {:name "Public Domain"
            :url "http://unlicense.org/UNLICENSE"}
  :dependencies [[nightlight "2.3.3-SNAPSHOT" :exclusions [org.clojure/core.async]]
                 [leinjacker "0.4.2"]
                 [org.clojure/tools.cli "0.3.5"]]
  :repositories [["clojars" {:url "https://clojars.org/repo"
                             :sign-releases false}]]
  :eval-in-leiningen true)

