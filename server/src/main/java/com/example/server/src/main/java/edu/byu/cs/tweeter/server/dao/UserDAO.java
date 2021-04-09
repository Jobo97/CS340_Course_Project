package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LoginRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {
    private final String TABLE_USER = "user";
    AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();

    private DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
    private Table table = dynamoDB.getTable(TABLE_USER);

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
        System.out.println(userAlias);
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("user_alias", userAlias);
        try {
            System.out.println("Attempting to read the item...");
            Item outcome = table.getItem(spec);
            User u = new User();
            u.setAlias(outcome.getString("user_alias"));
            u.setFirstName(outcome.getString("firstName"));
            u.setLastName(outcome.getString("lastName"));
            // s3dao to get image and convert into bytes
            u.setImageUrl(outcome.getString("imageUrl"));
            System.out.println("GetItem succeeded: " + outcome);
            System.out.println(u.toString());
            return u;
        }
        catch (Exception e) {
            System.err.println("Unable to read user: " + userAlias);
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void addUserBatch(List<User> users) {

        // Constructor for TableWriteItems takes the name of the table, which I have stored in TABLE_USER
        TableWriteItems items = new TableWriteItems(TABLE_USER);

        // Add each user into the TableWriteItems object
        for (User user : users) {
            Item item = new Item()
                    .withPrimaryKey("user_alias", user.getAlias())
                    .withString("firstName", user.getName())
                    .withString("lastName", user.getLastName())
                    .withString("imageUrl", user.getImageUrl());
            items.addItemToPut(item);

            // 25 is the maximum number of items allowed in a single batch write.
            // Attempting to write more than 25 items will result in an exception being thrown
            if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                loopBatchWrite(items);
                items = new TableWriteItems(TABLE_USER);
            }
        }

        // Write any leftover items
        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWrite(items);
        }
    }

    private void loopBatchWrite(TableWriteItems items) {

        // The 'dynamoDB' object is of type DynamoDB and is declared statically in this example
        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(items);
        System.out.println("Wrote User Batch");

        // Check the outcome for items that didn't make it onto the table
        // If any were not added to the table, try again to write the batch
        while (outcome.getUnprocessedItems().size() > 0) {
            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
            outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
            System.out.println("Wrote more Users");
        }
    }
}
