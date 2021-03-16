package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade_Old;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;
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

    ServerFacade_Old getServerFacade() {
        return new ServerFacade_Old();
    }
}
