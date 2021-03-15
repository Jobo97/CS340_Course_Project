package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request;

public class StatusRequest {
    private final String userAlias;
    private final int limit;
    private final String lastStatus;
    private final boolean isStory;


    public StatusRequest(String userAlias, int limit, String lastStatus, boolean isStory) {
        this.userAlias = userAlias;
        this.limit = limit;
        this.lastStatus = lastStatus;
        this.isStory = isStory;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public int getLimit() {
        return limit;
    }

    public boolean isStory() {
        return isStory;
    }
}
