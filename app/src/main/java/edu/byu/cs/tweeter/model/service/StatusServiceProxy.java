package edu.byu.cs.tweeter.model.service;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.Status;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class StatusServiceProxy {

    private static final String URL_PATH = "/statuses";

    public StatusResponse getStatuses(StatusRequest request) throws IOException {
        StatusResponse response = getServerFacade().getStatuses(request);
//        StatusResponse response = getServerFacade().getStatuses(request, URL_PATH);

        if(response.isSuccess()) {
            loadImages(response);
        }
        return response;
    }

    private void loadImages(StatusResponse response) throws IOException {
        for(Status status : response.getStatuses()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(status.getUser().getImageUrl());
            status.getUser().setImageBytes(bytes);
        }
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
