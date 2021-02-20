package edu.byu.cs.tweeter.model.service.response;

public class FollowCountResponse extends Response{

    private final int followerCount;
    private final int followeeCount;

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

    public int getFollowerCount() {
        return followerCount;
    }

    public int getFolloweeCount() {
        return followeeCount;
    }
}
