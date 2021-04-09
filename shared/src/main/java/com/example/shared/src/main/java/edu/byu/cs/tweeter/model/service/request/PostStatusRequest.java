package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request;

public class PostStatusRequest {
    private String tweet;
    private String alias;
    private String timeStampString;

    public PostStatusRequest(String tweet, String alias, String timeStampString) {
        this.tweet = tweet;
        this.alias = alias;
        this.timeStampString = timeStampString;
    }

    public PostStatusRequest() {
    }

    public String getTweet() {
        return tweet;
    }

    public String getAlias() {
        return alias;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
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
}
