package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request;

public class UserFollowRequest {
    private String userAlias;
    private String viewedAlias;

    public UserFollowRequest(String userAlias, String viewedAlias) {
        this.userAlias = userAlias;
        this.viewedAlias = viewedAlias;
    }

    public UserFollowRequest() {
    }

    public String getUserAlias() {
        return userAlias;
    }

    public String getViewedAlias() {
        return viewedAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public void setViewedAlias(String viewedAlias) {
        this.viewedAlias = viewedAlias;
    }
}
