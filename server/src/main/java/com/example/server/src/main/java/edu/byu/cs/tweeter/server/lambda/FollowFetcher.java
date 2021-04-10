package com.example.server.src.main.java.edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.MainServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowFetcher implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        for (SQSEvent.SQSMessage msg : sqsEvent.getRecords()) {
            PostStatusRequest postStatusRequest = new PostStatusRequest();
            Map<String, SQSEvent.MessageAttribute> map = msg.getMessageAttributes();
            postStatusRequest.setAlias(map.get("alias").getStringValue());
            postStatusRequest.setTweet(map.get("tweet").getStringValue());
            postStatusRequest.setTimeStampString(map.get("timeStampString").getStringValue());
            System.out.println("FollowFetcher message" + msg);

            MainServiceImpl service = new MainServiceImpl();
            List<User> followers = service.postStatus(postStatusRequest);
            if(followers != null){
                for(User user : followers){
                    String queueUrl = "https://sqs.us-west-2.amazonaws.com/797774218094/JobsQ";
                    Map<String, MessageAttributeValue> mapFollowers = new HashMap<>();
                    MessageAttributeValue followerAlias = new MessageAttributeValue();
                    followerAlias.setStringValue(user.getAlias());
                    MessageAttributeValue tweet = new MessageAttributeValue();
                    tweet.setStringValue(map.get("tweet").getStringValue());
                    MessageAttributeValue timeStampString = new MessageAttributeValue();
                    timeStampString.setStringValue(map.get("timeStampString").getStringValue());
                    MessageAttributeValue tweet_userAlias = new MessageAttributeValue();
                    tweet_userAlias.setStringValue(map.get("alias").getStringValue());
                    mapFollowers.put("followerAlias", followerAlias);
                    mapFollowers.put("tweet", tweet);
                    mapFollowers.put("timeStampString", timeStampString);
                    mapFollowers.put("tweet_userAlias", tweet_userAlias);

                    SendMessageRequest send_msg_request = new SendMessageRequest()
                            .withQueueUrl(queueUrl)
                            .withMessageAttributes(mapFollowers)
                            .withDelaySeconds(5);


                    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
                    SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);
                    String msgId = send_msg_result.getMessageId();
                    System.out.println("Message ID: " + msgId);

                }
            }
            else{
                // Job's done
            }
        }
        return null;
    }
}
