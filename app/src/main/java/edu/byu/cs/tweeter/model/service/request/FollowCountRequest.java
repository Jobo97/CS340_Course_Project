package edu.byu.cs.tweeter.model.service.request;

public class FollowCountRequest {
    private final String userAlias;

    public FollowCountRequest(String userAlias) {
        this.userAlias = userAlias;
    }

    public String getUserAlias() {
        return userAlias;
    }
}
