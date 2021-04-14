package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request;

public class FollowCountRequest {
    private String userAlias;
    private String loggedInAlias;

    public FollowCountRequest(String userAlias, String loggedInAlias) {
        this.userAlias = userAlias;
        this.loggedInAlias = loggedInAlias;
    }

    public String getLoggedInAlias() {
        return loggedInAlias;
    }

    public void setLoggedInAlias(String loggedInAlias) {
        this.loggedInAlias = loggedInAlias;
    }

    public FollowCountRequest(String userAlias) {
            this.userAlias = userAlias;
        }

    public FollowCountRequest() {}

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }
}
