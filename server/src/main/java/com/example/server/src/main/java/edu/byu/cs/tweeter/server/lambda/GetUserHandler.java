package com.example.server.src.main.java.edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.ProfileServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;

import java.io.IOException;

public class GetUserHandler implements RequestHandler<GetUserRequest, GetUserResponse> {
    @Override
    public GetUserResponse handleRequest(GetUserRequest input, Context context) {
        ProfileServiceImpl service = new ProfileServiceImpl();
        try {
            return service.getUser(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
