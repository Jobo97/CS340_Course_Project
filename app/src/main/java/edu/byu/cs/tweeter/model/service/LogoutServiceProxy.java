package edu.byu.cs.tweeter.model.service;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;

public class LogoutServiceProxy {

    private static final String URL_PATH = "/logout";


    public Response logout(LogoutRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        return serverFacade.logout(request);
        //return serverFacade.logout(request, URL_PATH);
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
