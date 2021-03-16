package com.example.server.src.main.java.edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.MainServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

public class PostStatusHandler implements RequestHandler<PostStatusRequest, Response> {
    @Override
    public Response handleRequest(PostStatusRequest input, Context context) {
        MainServiceImpl service = new MainServiceImpl();
        return service.postStatus(input);
    }
}
