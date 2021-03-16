package com.example.server.src.main.java.edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.StatusServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

import java.io.IOException;

public class GetStatusesHandler implements RequestHandler<StatusRequest, StatusResponse> {
    @Override
    public StatusResponse handleRequest(StatusRequest input, Context context) {
        StatusServiceImpl service = new StatusServiceImpl();
        try {
            return service.getStatuses(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
