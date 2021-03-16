package com.example.server.src.main.java.edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.FollowServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.UserFollowResponse;

import java.io.IOException;

public class CheckFollowHandler implements RequestHandler<UserFollowRequest, UserFollowResponse> {
    @Override
    public UserFollowResponse handleRequest(UserFollowRequest input, Context context) {
        FollowServiceImpl service = new FollowServiceImpl();
        try {
            return service.checkFollow(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
