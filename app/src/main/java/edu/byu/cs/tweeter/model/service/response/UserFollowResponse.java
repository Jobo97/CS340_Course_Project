package edu.byu.cs.tweeter.model.service.response;

public class UserFollowResponse extends Response{

    private final boolean isFollower;

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
}
