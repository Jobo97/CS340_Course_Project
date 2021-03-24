package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents an auth token in the system.
 */
public class AuthToken implements Serializable {
    private String authToken;

    public AuthToken() {}

    public AuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken);
    }
}
