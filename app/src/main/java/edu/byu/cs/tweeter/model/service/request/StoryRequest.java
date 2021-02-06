package edu.byu.cs.tweeter.model.service.request;

public class StoryRequest{
    private final String userAlias;
    private final int limit;


    public StoryRequest(String userAlias, int limit) {
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
