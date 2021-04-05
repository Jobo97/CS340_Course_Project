package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LoginRequest;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
    AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();

    private DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
    private Table table = dynamoDB.getTable("user");

    public boolean put(LoginRequest request) {
        final Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("username", request.getUsername());
        infoMap.put("password", request.getPassword());
        infoMap.put("firstname", request.getFirstname());
        infoMap.put("lastname", request.getLastname());
        //infoMap.put("imageEncoded", followee_name);

        try {
            PutItemOutcome outcome = table
                    .putItem(new Item()
                            .withPrimaryKey("user_alias", request.getUsername())
                            .withMap("info", infoMap));
        } catch(Exception e) {
            System.err.println("Unable to add user: " + request.getUsername());
            return false;
        }
        return true;
    }

    public User get(String userAlias) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("user_alias", userAlias);
        try {
            System.out.println("Attempting to read the item...");
            Item outcome = table.getItem(spec);
            User u = new User();
            u.setAlias(outcome.getString("user_alias"));
            u.setFirstName(outcome.getString("firstname"));
            u.setLastName(outcome.getString("lastname"));
            // s3dao to get image and convert into bytes
            System.out.println("GetItem succeeded: " + outcome);
            return u;
        }
        catch (Exception e) {
            System.err.println("Unable to read user: " + userAlias);
            System.err.println(e.getMessage());
            return null;
        }
    }
}
