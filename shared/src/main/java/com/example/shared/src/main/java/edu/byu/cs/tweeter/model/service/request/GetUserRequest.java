package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request;

public class GetUserRequest {
    private String useralias;
    private String loggedInUserAlias;

    public String getLoggedInUserAlias() {
        return loggedInUserAlias;
    }

    public void setLoggedInUserAlias(String loggedInUserAlias) {
        this.loggedInUserAlias = loggedInUserAlias;
    }

    public GetUserRequest(String useralias, String loggedInUserAlias) {
        this.useralias = useralias;
        this.loggedInUserAlias = loggedInUserAlias;
    }

    public GetUserRequest(String useralias) {
        this.useralias = useralias;
    }

    public GetUserRequest() {
    }

    public String getUseralias() {
        return useralias;
    }

    public void setUseralias(String useralias) {
        this.useralias = useralias;
    }
}
