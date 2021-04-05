package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DynamoFollow {
    private DynamoDB dynamoDB;
    private Table table;

    void put(String follower_handle, String followee_handle, String followee_name, String follower_name){
        final Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("follower_name", follower_name);
        infoMap.put("followee_name", followee_name);


        try {
            System.out.println("Adding a new item...");
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("follower_handle", follower_handle, "followee_handle", followee_handle).withMap("info", infoMap));

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());

        }
        catch (Exception e) {
            System.err.println("Unable to add item: " + follower_handle + " " + followee_handle);
            System.err.println(e.getMessage());
        }
    }

    void get(String follower_handle, String followee_handle){
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("follower_handle", follower_handle, "followee_handle", followee_handle);

        try {
            System.out.println("Attempting to read the item...");
            Item outcome = table.getItem(spec);
            System.out.println("GetItem succeeded: " + outcome);

        }
        catch (Exception e) {
            System.err.println("Unable to read item: " + follower_handle + " " + followee_handle);
            System.err.println(e.getMessage());
        }
    }

    void update(String follower_handle, String followee_handle){
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("follower_handle", follower_handle, "followee_handle", followee_handle)
                .withUpdateExpression("set info.follower_name = :r, info.followee_name=:e")
                .withValueMap(new ValueMap().withString(":r", "new_folower_name").withString(":e", "new_folowee_name"))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            System.out.println("Updating the item...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Unable to update item: " + follower_handle + " " + followee_handle);
            System.err.println(e.getMessage());
        }
    }

    void delete(String follower_handle, String followee_handle){
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("follower_handle", follower_handle, "followee_handle", followee_handle));


        try {
//            System.out.println("Attempting a conditional delete...");
            table.deleteItem(deleteItemSpec);
            System.out.println("DeleteItem succeeded");
        }
        catch (Exception e) {
            System.err.println("Unable to delete item: " + follower_handle + " " + followee_handle);
            System.err.println(e.getMessage());
        }
    }

    void getFollowers(String followee_handle){
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#yr", "followee_handle");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":yyyy", followee_handle);
        QuerySpec querySpec = new QuerySpec()
                .withScanIndexForward(false)
                .withKeyConditionExpression("#yr = :yyyy").withNameMap(nameMap)
                .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        try {
            System.out.println(followee_handle + "'s followers:");
            items = table.getIndex("follows_index").query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getString("follower_handle"));
            }

        }
        catch (Exception e) {
            System.err.println("Unable to query " + followee_handle + "'s followers!");
            System.err.println(e.getMessage());
        }
    }
//
    public void getFollowees(String follower_handle) {

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#yr", "follower_handle");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":yyyy", follower_handle);
        QuerySpec querySpec = new QuerySpec()
                .withScanIndexForward(true)
                .withKeyConditionExpression("#yr = :yyyy").withNameMap(nameMap)
                .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        try {
            System.out.println(follower_handle + "'s followees:");
            items = table.query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getString("followee_handle"));
            }

        }
        catch (Exception e) {
            System.err.println("Unable to query " + follower_handle + "'s followees!");
            System.err.println(e.getMessage());
        }
    }

    public List<User> getFollowersPaginated(String followee_handle, Integer pageSize) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#yr", "followee_handle");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":yyyy", followee_handle);
        QuerySpec querySpec = new QuerySpec()
                .withScanIndexForward(false)
                .withKeyConditionExpression("#yr = :yyyy").withNameMap(nameMap)
                .withValueMap(valueMap)
                .withMaxResultSize(pageSize);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;
        List<User> users = new ArrayList<>();

        try {
            System.out.println(followee_handle + "'s followers:");
            items = table.getIndex("follows_index").query(querySpec);
            while(items.getLastLowLevelResult() != null){
                System.out.println("additional page");
                querySpec = new QuerySpec()
                        .withScanIndexForward(false)
                        .withKeyConditionExpression("#yr = :yyyy").withNameMap(nameMap)
                        .withValueMap(valueMap)
                        .withMaxResultSize(pageSize)
                        .withExclusiveStartKey((KeyAttribute) items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey());
                items = table.getIndex("follows_index").query(querySpec);

            }

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                User u = new User();
                u.setFirstName(item.get("firstName").toString());
                u.setLastName(item.get("lastName").toString());
                u.setAlias(item.get("alias").toString());
                u.setImageUrl(item.get("imageUrl").toString());
                users.add(u);
            }

        }
        catch (Exception e) {
            System.err.println("Unable to query " + followee_handle + "'s followers!");
            System.err.println(e.getMessage());
        }
        return users;
    }

    public List<User> getFolloweesPaginated(String follower_handle, Integer pageSize) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#yr", "follower_handle");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":yyyy", follower_handle);
        QuerySpec querySpec = new QuerySpec()
                .withScanIndexForward(true)
                .withKeyConditionExpression("#yr = :yyyy").withNameMap(nameMap)
                .withValueMap(valueMap)
                .withMaxResultSize(pageSize);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;
        List<User> users = new ArrayList<>();

        try {
            System.out.println(follower_handle + "'s followees:");
            items = table.query(querySpec);
            while(items.getLastLowLevelResult() != null){
                System.out.println("additional page");
                querySpec = new QuerySpec()
                        .withScanIndexForward(true)
                        .withKeyConditionExpression("#yr = :yyyy").withNameMap(nameMap)
                        .withValueMap(valueMap)
                        .withMaxResultSize(pageSize)
                        .withExclusiveStartKey((KeyAttribute) items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey());
                items = table.getIndex("follows_index").query(querySpec);
            }

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                User u = new User();
                u.setFirstName(item.get("firstName").toString());
                u.setLastName(item.get("lastName").toString());
                u.setAlias(item.get("alias").toString());
                u.setImageUrl(item.get("imageUrl").toString());
                users.add(u);
            }

        }
        catch (Exception e) {
            System.err.println("Unable to query " + follower_handle + "'s followees!");
            System.err.println(e.getMessage());
        }
        return users;
    }
}
