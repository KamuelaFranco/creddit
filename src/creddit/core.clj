(ns creddit.core
  (:require [creddit.client :as client]))

(defprotocol RedditApi
  (frontpage [this limit time])
  (controversial [this limit time])
  (new [this limit time])
  (rising [this limit time])
  (top [this limit time])
  (subreddit [this subreddit limit time])
  (subreddit-controversial [this subreddit limit time])
  (subreddit-new [this subreddit limit time])
  (subreddit-rising [this subreddit limit time])
  (subreddit-top [this subreddit limit time])
  (subreddit-comments [this subreddit limit])
  (subreddit-search [this subreddit query limit])
  (subreddit-about [this subreddit])
  (subreddit-moderators [this subreddit])
  (subreddits [this limit])
  (subreddits-new [this limit])
  (subreddits-popular [this limit])
  (subreddits-gold [this limit])
  (subreddits-default [this limit])
  (subreddits-search [this subreddit limit])
  (user [this username])
  (user-trophies [this username])
  (user-posts [this username limit time])
  (user-comments [this username limit time])
  (users [this limit])
  (users-new [this limit])
  (users-popular [this limit])
  (listing [this names]))

(defrecord CredditClient [credentials]
  RedditApi
  (frontpage [this limit time] (client/frontpage credentials limit time))
  (controversial [this limit time] (client/controversial credentials limit time))
  (new [this limit time] (client/new credentials limit time))
  (rising [this limit time] (client/rising credentials limit time))
  (top [this limit time] (client/top credentials limit time))
  (subreddit [this subreddit limit time] (client/subreddit credentials subreddit limit time))
  (subreddit-controversial [this subreddit limit time] (client/subreddit-controversial credentials subreddit limit time))
  (subreddit-new [this subreddit limit time] (client/subreddit-new credentials subreddit limit time))
  (subreddit-rising [this subreddit limit time] (client/subreddit-rising credentials subreddit limit time))
  (subreddit-top [this subreddit limit time] (client/subreddit-top credentials subreddit limit time))
  (subreddit-comments [this subreddit limit] (client/subreddit-comments credentials subreddit limit))
  (subreddit-search [this subreddit query limit] (client/subreddit-search credentials subreddit query limit))
  (subreddit-about [this subreddit] (client/subreddit-about credentials subreddit))
  (subreddit-moderators [this subreddit] (client/subreddit-moderators credentials subreddit))
  (subreddits [this limit] (client/subreddits credentials limit))
  (subreddits-new [this limit] (client/subreddits-new credentials limit))
  (subreddits-popular [this limit] (client/subreddits-popular credentials limit))
  (subreddits-gold [this limit] (client/subreddits-gold credentials limit))
  (subreddits-default [this limit] (client/subreddits-default credentials limit))
  (subreddits-search [this subreddit limit] (client/subreddits-search credentials subreddit limit))
  (user [this username] (client/user credentials username))
  (user-trophies [this username] (client/user-trophies credentials username))
  (user-posts [this username limit time] (client/user-posts credentials username limit time))
  (user-comments [this username limit time] (client/user-comments credentials username limit time))
  (users [this limit] (client/users credentials limit))
  (users-new [this limit] (client/users-new credentials limit))
  (users-popular [this limit] (client/users-popular credentials limit))
  (listing [this names] (client/listing credentials names)))

(defn init
  [credentials]
  (let [response (client/get-access-token credentials)]
    (-> credentials
        (assoc :access-token (:access_token response))
        (assoc :expires-in (+ (System/currentTimeMillis) (:expires_in response)))
        (CredditClient.))))
