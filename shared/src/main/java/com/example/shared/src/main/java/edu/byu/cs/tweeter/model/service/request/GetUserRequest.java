package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request;

public class GetUserRequest {
    private String useralias;

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
