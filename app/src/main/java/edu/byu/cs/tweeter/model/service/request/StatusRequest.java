package edu.byu.cs.tweeter.model.service.request;

public class StatusRequest {
    private final String userAlias;
    private final int limit;
    private final boolean isStory;


    public StatusRequest(String userAlias, int limit, boolean isStory) {
        this.userAlias = userAlias;
        this.limit = limit;
        this.isStory = isStory;
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
