package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request;

public class PostStatusRequest {
    private String tweet;
    private String alias;

    public PostStatusRequest(String tweet, String alias) {
        this.tweet = tweet;
        this.alias = alias;
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
}
