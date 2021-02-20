package edu.byu.cs.tweeter.model.service.request;

public class UserFollowRequest {
    private final String userAlias;
    private final String viewedAlias;

    public UserFollowRequest(String userAlias, String viewedAlias) {
        this.userAlias = userAlias;
        this.viewedAlias = viewedAlias;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public String getViewedAlias() {
        return viewedAlias;
    }
}
