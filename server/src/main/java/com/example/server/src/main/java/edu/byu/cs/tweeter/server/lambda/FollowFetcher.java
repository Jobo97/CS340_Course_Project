package com.example.server.src.main.java.edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.dynamodbv2.xspec.S;
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
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.JsonSerializer;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.SQSRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowFetcher implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        for (SQSEvent.SQSMessage msg : sqsEvent.getRecords()) {
            PostStatusRequest postStatusRequest = JsonSerializer
                    .deserialize(msg.getBody(), PostStatusRequest.class);

            System.out.println("FollowFetcher message" + msg.getBody());

            MainServiceImpl service = new MainServiceImpl();
            List<String> followers = service.postStatus(postStatusRequest);
            System.out.println("Number of followers:");
            if(followers != null){
                List<String> subGroup = new ArrayList<>();
                String queueUrl = "https://sqs.us-west-2.amazonaws.com/797774218094/JobsQ";
                for(String s : followers){
                    subGroup.add(s);
                    if(subGroup.size() != 25){
                        continue;
                    }

                    SQSRequest sqsRequest = new SQSRequest(subGroup, postStatusRequest.getTweet(),
                            postStatusRequest.getTimeStampString(), postStatusRequest.getAlias());
                    String jsonRequest = JsonSerializer.serialize(sqsRequest);


                    SendMessageRequest send_msg_request = new SendMessageRequest()
                            .withQueueUrl(queueUrl)
                            .withMessageBody(jsonRequest)
                            .withDelaySeconds(5);

                    System.out.println("send_msg_request");
                    System.out.println(send_msg_request.toString());
                    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
                    SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);
                    String msgId = send_msg_result.getMessageId();
                    System.out.println("Message ID: " + msgId);

                    // Clearing the sub group.
                    subGroup.clear();

                }
                // If there are left overs, send one more message.
                if(!subGroup.isEmpty()) {

                    SQSRequest sqsRequest = new SQSRequest(subGroup, postStatusRequest.getTweet(),
                            postStatusRequest.getTimeStampString(), postStatusRequest.getAlias());
                    String jsonRequest = JsonSerializer.serialize(sqsRequest);

                    SendMessageRequest send_msg_request = new SendMessageRequest()
                            .withQueueUrl(queueUrl)
                            .withMessageBody(jsonRequest)
                            .withDelaySeconds(5);

                    System.out.println("send_msg_request");
                    System.out.println(send_msg_request.toString());
                    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
                    SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);
                    String msgId = send_msg_result.getMessageId();
                    System.out.println("Message ID: " + msgId);

                    // Clearing the sub group.
                    subGroup.clear();
                }
            }
        }
        return null;
    }
}
