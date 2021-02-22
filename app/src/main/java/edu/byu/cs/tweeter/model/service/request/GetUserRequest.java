package edu.byu.cs.tweeter.model.service.request;

public class GetUserRequest {
    private final String useralias;

    public GetUserRequest(String useralias) {
        this.useralias = useralias;
    }

    public String getUseralias() {
        return useralias;
    }
}
