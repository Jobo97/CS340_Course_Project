package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedService {

    public FeedResponse getFeed(FeedRequest request) throws IOException {
        FeedResponse response = getServerFacade().getFeed(request);
        return response;
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
