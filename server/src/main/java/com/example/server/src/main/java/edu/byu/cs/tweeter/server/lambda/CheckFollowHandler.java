package com.example.server.src.main.java.edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.FollowServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;

import java.io.IOException;

public class CheckFollowHandler implements RequestHandler<FollowRequest, FollowResponse> {
    @Override
    public FollowResponse handleRequest(FollowRequest input, Context context) {
        FollowServiceImpl service = new FollowServiceImpl();
        try {
            return service.getFollows(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
