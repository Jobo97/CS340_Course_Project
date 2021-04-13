package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request;

import java.util.List;

public class SQSRequest {
    private List<String> userAliases;
    private String tweet;
    private String timeStampString;
    private String tweet_user_alias;

    public SQSRequest(){}

    public SQSRequest(List<String> userAliases, String tweet, String timeStampString, String tweet_user_alias) {
        this.userAliases = userAliases;
        this.tweet = tweet;
        this.timeStampString = timeStampString;
        this.tweet_user_alias = tweet_user_alias;
    }

    public List<String> getUserAliases() {
        return userAliases;
    }

    public void setUserAliases(List<String> userAliases) {
        this.userAliases = userAliases;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getTimeStampString() {
        return timeStampString;
    }

    public void setTimeStampString(String timeStampString) {
        this.timeStampString = timeStampString;
    }

    public String getTweet_user_alias() {
        return tweet_user_alias;
    }

    public void setTweet_user_alias(String tweet_user_alias) {
        this.tweet_user_alias = tweet_user_alias;
    }

    @Override
    public String toString() {
        return "SQSRequest{" +
                "userAliases=" + userAliases +
                ", tweet='" + tweet + '\'' +
                ", timeStampString='" + timeStampString + '\'' +
                ", tweet_user_alias='" + tweet_user_alias + '\'' +
                '}';
    }
}
