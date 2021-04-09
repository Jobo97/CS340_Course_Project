package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

public class MainDAO {

    private StatusDAO statusDAO = new StatusDAO();

    // When I post a tweet, it needs to be added to the Story table in relation to me
    // Then needs to be placed in the feeds table in relation to all of my followers
    // To do the feeds table, I'll need to get all of my followers from follow DAO
    public Response postStatus(PostStatusRequest request) {

        try {
            boolean addedToStory = statusDAO.putStory(request.getAlias(), request.getTweet(), request.getTimeStampString());
            boolean addedToFeed = statusDAO.putFeed(request.getAlias(), request.getTweet(), request.getTimeStampString());
            if(addedToFeed && addedToStory){
                return new Response(true, "Tweet successfully added to story/feeds");
            }
            else {
                return new Response(false, "Unable to post Tweet");
            }
        }
        catch (Exception e) {
            System.err.println("Unable to post " + request.getAlias() + "'s tweet due to a connection error");
            System.err.println(e.getMessage());
            return new Response(false, "Connection error");
        }

        //would add the tweet to the data base
//        if(request.getAlias() == null || request.getTweet() == null){
//            return new Response(false);
//        }
//        return new Response(true);

    }
}
