package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryService {

    public StoryResponse getStory(StoryRequest request) throws IOException {
        StoryResponse response = getServerFacade().getStory(request);
        return response;
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
