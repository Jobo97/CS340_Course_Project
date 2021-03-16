package com.example.server.src.main.java.edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.LogoutServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

import java.io.IOException;

public class LogoutHandler implements RequestHandler<LogoutRequest, Response> {
    @Override
    public Response handleRequest(LogoutRequest input, Context context) {
        LogoutServiceImpl service = new LogoutServiceImpl();
        try {
            return service.logout(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
