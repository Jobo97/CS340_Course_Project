package edu.byu.cs.tweeter.model.service;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class ProfileServiceProxy {

    private static final String URL_PATH = "/profile";


    public GetUserResponse getUser(GetUserRequest request) throws IOException {
        GetUserResponse response = getServerFacade().getUser(request);
//        GetUserResponse response = getServerFacade().getUser(request, URL_PATH);
        if(response.isSuccess()) {
            loadImages(response);
        }
        return response;
    }

    private void loadImages(GetUserResponse response) throws IOException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(response.getViewedUser().getImageUrl());
        response.getViewedUser().setImageBytes(bytes);
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
