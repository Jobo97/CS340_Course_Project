package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response;

public class FollowCountResponse extends Response{

    private int followerCount;
    private int followeeCount;

    public FollowCountResponse(boolean success, int followerCount, int followeeCount) {
        super(success);
        this.followerCount = followerCount;
        this.followeeCount = followeeCount;
    }

    FollowCountResponse(boolean success, String message, int followerCount, int followeeCount) {
        super(success, message);
        this.followerCount = followerCount;
        this.followeeCount = followeeCount;
    }

    public FollowCountResponse(){

    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public void setFolloweeCount(int followeeCount) {
        this.followeeCount = followeeCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public int getFolloweeCount() {
        return followeeCount;
    }
}
