package edu.byu.cs.tweeter.model.service.request;

public class FeedRequest {
    private final String userAlias;
    private final int limit;


    public FeedRequest(String userAlias, int limit) {
        this.userAlias = userAlias;
        this.limit = limit;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public int getLimit() {
        return limit;
    }
}
