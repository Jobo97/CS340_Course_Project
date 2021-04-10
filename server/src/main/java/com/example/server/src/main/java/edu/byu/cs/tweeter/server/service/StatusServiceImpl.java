package com.example.server.src.main.java.edu.byu.cs.tweeter.server.service;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.FeedDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.StoryDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.IStatusService;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusFeedRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

import java.io.IOException;

public class StatusServiceImpl implements IStatusService {
    @Override
    public StatusResponse getStatuses(StatusRequest request) throws IOException {
        if(request.getUserAlias() == null){
            return new StatusResponse(null, false);
        }
        if(request.getStory()){
            return getStoryDAO().getStoryPaginated(request.getUserAlias(), request.getLimit());
        }
        else{
            return getFeedDAO().getFeedPaginated(request.getUserAlias(), request.getLimit());
        }
    }

    @Override
    public boolean postStatusFeed(PostStatusFeedRequest postStatusFeedRequest) {
        return getFeedDAO().putFeed(postStatusFeedRequest);
    }

    public StoryDAO getStoryDAO(){ return new StoryDAO(); }
    public FeedDAO getFeedDAO(){ return new FeedDAO(); }
}
