package com.example.server.src.main.java.edu.byu.cs.tweeter.server.service;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.FeedDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.StoryDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.IStatusService;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusFeedRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

import java.io.IOException;
import java.util.List;

public class StatusServiceImpl implements IStatusService {
    @Override
    public StatusResponse getStatuses(StatusRequest request) throws IOException {
        if (getAuthTokenDAO().validateSession(request.getLoggedInUserAlias())) {
            if (request.getUserAlias() == null) {
                return new StatusResponse(null, false);
            }
            if (request.getStory()) {
                return getStoryDAO().getStoryPaginated(request.getUserAlias(), request.getLimit(), request.getLastTimeStamp());
            } else {
                return getFeedDAO().getFeedPaginated(request.getUserAlias(), request.getLimit(), request.getLastTimeStamp());
            }
        }
        else {
            StatusResponse response = new StatusResponse();
            response.setMessage("User-session timeout");
            response.setSuccess(false);
            return response;
        }
    }

    @Override
    public boolean postStatusFeed(List<PostStatusFeedRequest> postStatusFeedRequest) {
        return getFeedDAO().putFeed(postStatusFeedRequest);
    }

    public StoryDAO getStoryDAO(){ return new StoryDAO(); }
    public FeedDAO getFeedDAO(){ return new FeedDAO(); }
    public AuthTokenDAO getAuthTokenDAO(){ return new AuthTokenDAO(); }
}
