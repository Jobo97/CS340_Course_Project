package com.example.server.src.main.java.edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.MainServiceImpl;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.StatusServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.JsonSerializer;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusFeedRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.SQSRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JobHandler implements RequestHandler<SQSEvent,Void> {
    List<PostStatusFeedRequest> postStatusFeedRequestList = new ArrayList<>();

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        for (SQSEvent.SQSMessage msg : sqsEvent.getRecords()) {
            String serialized = msg.getBody();
            SQSRequest sqsRequest = JsonSerializer.deserialize(serialized, SQSRequest.class);
            for(String s : sqsRequest.getUserAliases()){
                PostStatusFeedRequest postStatusFeedRequest = new PostStatusFeedRequest();
                postStatusFeedRequest.setAlias(s);
                postStatusFeedRequest.setTweet_alias(sqsRequest.getTweet_user_alias());
                postStatusFeedRequest.setTweet(sqsRequest.getTweet());
                postStatusFeedRequest.setTimeStampString(sqsRequest.getTimeStampString());
                postStatusFeedRequestList.add(postStatusFeedRequest);
            }
            StatusServiceImpl service = new StatusServiceImpl();
            service.postStatusFeed(postStatusFeedRequestList);
        }
        postStatusFeedRequestList.clear();

        System.out.println("JobHandler message completed");

        return null;

    }
}

