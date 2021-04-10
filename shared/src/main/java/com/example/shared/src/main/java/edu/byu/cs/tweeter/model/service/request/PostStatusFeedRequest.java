package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request;

public class PostStatusFeedRequest {
    private String tweet;
    private String alias;
    private String timeStampString;
    private String tweet_alias;

    public PostStatusFeedRequest(){}

    public PostStatusFeedRequest(String tweet, String alias, String timeStampString, String tweet_alias) {
        this.tweet = tweet;
        this.alias = alias;
        this.timeStampString = timeStampString;
        this.tweet_alias = tweet_alias;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTimeStampString() {
        return timeStampString;
    }

    public void setTimeStampString(String timeStampString) {
        this.timeStampString = timeStampString;
    }

    public String getTweet_alias() {
        return tweet_alias;
    }

    public void setTweet_alias(String tweet_alias) {
        this.tweet_alias = tweet_alias;
    }

    @Override
    public String toString() {
        return "PostStatusFeedRequest{" +
                "tweet='" + tweet + '\'' +
                ", alias='" + alias + '\'' +
                ", timeStampString='" + timeStampString + '\'' +
                ", tweet_alias='" + tweet_alias + '\'' +
                '}';
    }
}
