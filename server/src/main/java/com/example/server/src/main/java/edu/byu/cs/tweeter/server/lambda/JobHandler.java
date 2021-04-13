package com.example.server.src.main.java.edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.MainServiceImpl;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.StatusServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusFeedRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;

import java.util.List;
import java.util.Map;

public class JobHandler implements RequestHandler<SQSEvent,Void> {
    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        for (SQSEvent.SQSMessage msg : sqsEvent.getRecords()) {
            PostStatusFeedRequest postStatusFeedRequest = new PostStatusFeedRequest();
            Map<String, SQSEvent.MessageAttribute> map = msg.getMessageAttributes();

            //Check for deserializing errors here

            postStatusFeedRequest.setAlias(map.get("followerAlias").getStringValue());
            postStatusFeedRequest.setTweet_alias(map.get("tweet_userAlias").getStringValue());
            postStatusFeedRequest.setTweet(map.get("tweet").getStringValue());
            postStatusFeedRequest.setTimeStampString(map.get("timeStampString").getStringValue());

            System.out.println("JobHandler request info");
            System.out.println(postStatusFeedRequest.toString());

            StatusServiceImpl service = new StatusServiceImpl();
            service.postStatusFeed(postStatusFeedRequest);
        }
        System.out.println("JobHandler message completed");

        return null;

    }
}

