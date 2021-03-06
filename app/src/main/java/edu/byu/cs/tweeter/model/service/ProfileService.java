package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class ProfileService {

    public GetUserResponse getUser(GetUserRequest request) throws IOException{
        GetUserResponse response = getServerFacade().getUser(request);
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
