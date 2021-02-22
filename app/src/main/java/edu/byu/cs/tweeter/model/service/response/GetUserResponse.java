package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class GetUserResponse extends Response{

    private final User viewedUser;

    public GetUserResponse(boolean success, User viewedUser) {
        super(success);
        this.viewedUser = viewedUser;
    }

    GetUserResponse(boolean success, String message, User viewedUser) {
        super(success, message);
        this.viewedUser = viewedUser;
    }

    public User getViewedUser() {
        return viewedUser;
    }
}
