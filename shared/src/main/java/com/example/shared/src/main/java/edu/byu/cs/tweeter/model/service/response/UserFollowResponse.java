package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response;

public class UserFollowResponse extends Response{

    private boolean isFollower;

    public UserFollowResponse(){}

    public UserFollowResponse(boolean success, boolean isFollower) {
        super(success);
        this.isFollower = isFollower;
    }

    UserFollowResponse(boolean success, String message, boolean isFollower) {
        super(success, message);
        this.isFollower = isFollower;
    }

    public boolean isFollower() {
        return isFollower;
    }

    public void setFollower(boolean follower) {
        isFollower = follower;
    }
}
