package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request;

public class StatusRequest {
    private String userAlias;
    private int limit;
    private String lastTimeStamp;
    private boolean story;
    private String loggedInUserAlias;

    public String getLoggedInUserAlias() {
        return loggedInUserAlias;
    }

    public void setLoggedInUserAlias(String loggedInUserAlias) {
        this.loggedInUserAlias = loggedInUserAlias;
    }

    public StatusRequest(String userAlias, int limit, String lastTimeStamp, boolean story, String loggedInUserAlias) {
        this.userAlias = userAlias;
        this.limit = limit;
        this.lastTimeStamp = lastTimeStamp;
        this.story = story;
        this.loggedInUserAlias = loggedInUserAlias;
    }

    public StatusRequest(String userAlias, int limit, String lastTimeStamp, boolean story) {
        this.userAlias = userAlias;
        this.limit = limit;
        this.lastTimeStamp = lastTimeStamp;
        this.story = story;
    }

    public StatusRequest() {
    }

    public String getLastTimeStamp() {
        return lastTimeStamp;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public int getLimit() {
        return limit;
    }

    public boolean getStory() {
        return story;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastTimeStamp(String lastStatus) {
        this.lastTimeStamp = lastStatus;
    }

    public void setStory(boolean story) {
        this.story = story;
    }
}
