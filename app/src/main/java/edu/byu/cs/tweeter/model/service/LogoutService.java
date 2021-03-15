package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

public class LogoutService {
    public Response logout(LogoutRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        return serverFacade.logout(request);
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
