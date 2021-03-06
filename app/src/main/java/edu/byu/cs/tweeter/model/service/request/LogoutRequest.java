package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.service.response.Response;

public class LogoutRequest{
    private String userAlias;
    private AuthToken token;

    public LogoutRequest(String userAlias, AuthToken token) {
        this.userAlias = userAlias;
        this.token = token;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public AuthToken getToken() {
        return token;
    }
}
