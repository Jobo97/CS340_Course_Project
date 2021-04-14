package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified follower.
 */
public class FollowRequest {
    private String followerAlias;
    private int limit;
    private String lastFolloweeAlias;
    private boolean follower;
    private String loggedInUserAlias;

    public String getLoggedInUserAlias() {
        return loggedInUserAlias;
    }

    public void setLoggedInUserAlias(String loggedInUserAlias) {
        this.loggedInUserAlias = loggedInUserAlias;
    }

    /**
     * Creates an instance.
     *
     * @param followerAlias the alias of the user whose followees are to be returned.
     * @param limit the maximum number of followees to return.
     * @param lastFolloweeAlias the alias of the last followee that was returned in the previous request (null if
     *                     there was no previous request or if no followees were returned in the
     *                     previous request).
     */
    public FollowRequest(String followerAlias, int limit, String lastFolloweeAlias, boolean follower, String loggedInUserAlias) {
        this.followerAlias = followerAlias;
        this.limit = limit;
        this.lastFolloweeAlias = lastFolloweeAlias;
        this.follower = follower;
        this.loggedInUserAlias = loggedInUserAlias;
    }

    public FollowRequest(String followerAlias, int limit, String lastFolloweeAlias, boolean follower) {
        this.followerAlias = followerAlias;
        this.limit = limit;
        this.lastFolloweeAlias = lastFolloweeAlias;
        this.follower = follower;
    }

    public FollowRequest() {}

    /**
     * Returns the follower whose followees are to be returned by this request.
     *
     * @return the follower.
     */
    public String getFollowerAlias() {
        return followerAlias;
    }

    /**
     * Returns the number representing the maximum number of followees to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Returns the last followee that was returned in the previous request or null if there was no
     * previous request or if no followees were returned in the previous request.
     *
     * @return the last followee.
     */
    public String getLastFolloweeAlias() {
        return lastFolloweeAlias;
    }

    public boolean getFollower() {
        return follower;
    }

    public void setFollowerAlias(String followerAlias) {
        this.followerAlias = followerAlias;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastFolloweeAlias(String lastFolloweeAlias) {
        this.lastFolloweeAlias = lastFolloweeAlias;
    }

    public void setFollower(boolean follower) {
        this.follower = follower;
    }
}
