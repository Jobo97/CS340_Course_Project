package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusFeedRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

import java.io.IOException;

public interface IStatusService {
    StatusResponse getStatuses(StatusRequest request) throws IOException;

    boolean postStatusFeed(PostStatusFeedRequest postStatusFeedRequest);
}
