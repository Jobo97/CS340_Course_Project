package edu.byu.cs.tweeter.model.service.request;

public class PostStatusRequest {
    private final String tweet;
    private final String alias;

    public PostStatusRequest(String tweet, String alias) {
        this.tweet = tweet;
        this.alias = alias;
    }

    public String getTweet() {
        return tweet;
    }

    public String getAlias() {
        return alias;
    }
}
