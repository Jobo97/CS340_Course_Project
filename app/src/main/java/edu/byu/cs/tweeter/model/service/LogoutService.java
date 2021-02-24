package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class LogoutService {
    public Response logout(LogoutRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        return serverFacade.logout(request);
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
