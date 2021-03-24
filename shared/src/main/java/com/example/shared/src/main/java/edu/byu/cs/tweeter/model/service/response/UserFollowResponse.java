package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response;

public class UserFollowResponse extends Response{

    private boolean follower;

    public UserFollowResponse(){}

    public UserFollowResponse(boolean success, boolean follower) {
        super(success);
        this.follower = follower;
    }

    UserFollowResponse(boolean success, String message, boolean follower) {
        super(success, message);
        this.follower = follower;
    }

    public boolean getFollower() {
        return follower;
    }

    public void setFollower(boolean follower) {
        this.follower = follower;
    }
}
