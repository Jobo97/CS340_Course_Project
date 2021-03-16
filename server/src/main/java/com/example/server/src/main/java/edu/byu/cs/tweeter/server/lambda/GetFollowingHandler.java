package com.example.server.src.main.java.edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.FollowServiceImpl;

import java.io.IOException;

/**
 * An AWS lambda function that returns the users a user is following.
 */
public class GetFollowingHandler implements RequestHandler<FollowRequest, FollowResponse> {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the followees.
     */
    @Override
    public FollowResponse handleRequest(FollowRequest request, Context context) {
        FollowServiceImpl service = new FollowServiceImpl();
        try {
            return service.getFollows(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
