package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request;

public class StatusRequest {
    private String userAlias;
    private int limit;
    private String lastStatus;
    private boolean isStory;


    public StatusRequest(String userAlias, int limit, String lastStatus, boolean isStory) {
        this.userAlias = userAlias;
        this.limit = limit;
        this.lastStatus = lastStatus;
        this.isStory = isStory;
    }

    public StatusRequest() {
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

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    public void setStory(boolean story) {
        isStory = story;
    }
}
