package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.UserFollowResponse;

import java.util.HashMap;

public class LogoutDAO {

//    AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
//            .standard()
//            .withRegion("us-west-2")
//            .build();
//
//    private DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
//    private Table table = dynamoDB.getTable("authtoken");

    public Response logout(LogoutRequest request) {

        AuthTokenDAO aDao = new AuthTokenDAO();
        if (aDao.delete(request.getUserAlias())) {
            System.out.println("Logout succeeded");
            return new Response(true, "Logout successful!");
        }
        else {
            System.err.println("Unable to logout user: " + request.getUserAlias() + " still logged in.");
            return new Response(false, "Logout failed.");
        }
    }
}

//        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
//                .withPrimaryKey(new PrimaryKey("user_alias", request.getUserAlias()));
//        try {
//            System.out.println("Attempting logout...");
//            table.deleteItem(deleteItemSpec);
//            System.out.println("Logout succeeded");
//            return new Response(true, "Logout successful!");
//        }
//        catch (Exception e) {
//            System.err.println("Unable to logout user: " + request.getUserAlias() + " still logged in.");
//            System.err.println(e.getMessage());
//            return new Response(false, "Logout failed.");
//        }
