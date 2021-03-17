package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;

public class LogoutRequest{
    private String userAlias;
    private AuthToken token;

    public LogoutRequest(String userAlias, AuthToken token) {
        this.userAlias = userAlias;
        this.token = token;
    }

    public LogoutRequest() {
    }

    public String getUserAlias() {
        return userAlias;
    }

    public AuthToken getToken() {
        return token;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public void setToken(AuthToken token) {
        this.token = token;
    }
}
