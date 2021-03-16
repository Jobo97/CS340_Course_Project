package com.example.server.src.main.java.edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.FollowServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowCountResponse;

import java.io.IOException;

public class GetFollowCountHandler implements RequestHandler<FollowCountRequest, FollowCountResponse> {
    @Override
    public FollowCountResponse handleRequest(FollowCountRequest input, Context context) {
        FollowServiceImpl service = new FollowServiceImpl();
        try {
            return service.getFollowCount(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
