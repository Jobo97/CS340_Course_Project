package edu.byu.cs.tweeter.model.service.request;

public class FollowerRequest {
    private final String followerAlias;
    private final int limit;
    private final String lastFollowerAlias;

    public FollowerRequest(String followerAlias, int limit, String lastFollowerAlias) {
        this.followerAlias = followerAlias;
        this.limit = limit;
        this.lastFollowerAlias = lastFollowerAlias;
    }

    public String getFollowerAlias() {
        return followerAlias;
    }

    public int getLimit() {
        return limit;
    }

    public String getLastFollowerAlias() {
        return lastFollowerAlias;
    }
}
