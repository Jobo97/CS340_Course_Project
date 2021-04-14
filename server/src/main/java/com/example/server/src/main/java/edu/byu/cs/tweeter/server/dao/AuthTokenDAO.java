package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthTokenDAO {

    private final String TABLE_AUTH = "authtoken";
    private final long TIME_LIMIT = 14400000; // 4 hours
    AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();

    private DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
    private Table table = dynamoDB.getTable(TABLE_AUTH);

    // put
    // update
    // get
    // delete

    public boolean validateSession(String userAlias) {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        Date date = new Date(System.currentTimeMillis());
        try{
            date = dateFormat.parse(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        Timestamp timestamp = new Timestamp(date.getTime());
        long timestampLong = timestamp.getTime();
        String timestampStr = timestamp.toString();
        System.out.println("TIMESTAMPSTR:");
        System.out.println(timestampStr);

        String tableTimeStamp = get(userAlias);
        System.out.println("TIMESTAMPTABLE:");
        System.out.println(tableTimeStamp);
        Timestamp timestampTable;
        try {
            SimpleDateFormat dateFormatTable = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormatTable.parse(tableTimeStamp);
            timestampTable = new Timestamp(parsedDate.getTime());
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        long tableTimeStampLong = timestampTable.getTime();
//        try {
//            Date parsedDate = dateFormat.parse(tableTimeStamp);
//            Timestamp timestampOld = new Timestamp(parsedDate.getTime());
//            tableTimeStampLong = timestampOld.getTime();
//
//        } catch(Exception e) { //this generic but you can control another types of exception}
//            e.printStackTrace();
//            return false;
//        }

        System.out.println(timestampLong);
        System.out.println(tableTimeStampLong);
        System.out.println((timestampLong - tableTimeStampLong) < TIME_LIMIT);
        if ((timestampLong - tableTimeStampLong) < TIME_LIMIT) {
            update(userAlias, timestampStr);
            return true;
        }
        else {
            //delete(userAlias);

            //logout
//            LogoutDAO logout = new LogoutDAO();
//            logout.logout(new LogoutRequest(userAlias, null));
            return false;
        }
    }

    public String get(String userAlias) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("user_alias", userAlias);
        try {
            System.out.println("Attempting to read the item...");
            Item outcome = table.getItem(spec);

            // s3dao to get image and convert into bytes
            System.out.println("GetItem succeeded: " + outcome);
            return outcome.getString("timestamp");
        }
        catch (Exception e) {
            System.err.println("Unable to read authtoken: " + userAlias);
            System.err.println(e.getMessage());
            return null;
        }
    }

    public boolean put(String userAlias, String authToken) {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        Date date = new Date(System.currentTimeMillis());
        try{
            date = dateFormat.parse(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestamp = new Timestamp(date.getTime());
        String timestampStr = timestamp.toString();

        try {
            PutItemOutcome outcome = table
                    .putItem(new Item()
                            .withPrimaryKey("user_alias", userAlias)
                            .withString("authtoken", authToken)
                            .withString("timestamp", timestampStr));
        } catch(Exception e) {
            System.err.println("Unable to add authtoken: " + userAlias);
            return false;
        }
        return true;
    }

    public boolean update(String userAlias, String timestamp) {
        System.out.println("TIMESTAMPSTR UPDATE:");
        System.out.println(timestamp);
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("user_alias", userAlias)
                .withUpdateExpression("set #ts = :r")
                .withNameMap(new NameMap().with("#ts", "timestamp"))
                .withValueMap(new ValueMap().withString(":r", timestamp))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            System.out.println("Updating the item...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
            return true;
        }
        catch (Exception e) {
            System.err.println("Unable to update authToken: " + userAlias);
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(String userAlias){
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("user_alias", userAlias));
        try {
            System.out.println("Attempting a conditional delete...");
            table.deleteItem(deleteItemSpec);
            System.out.println("DeleteItem succeeded");
            return true;
        }
        catch (Exception e) {
            System.err.println("Unable to delete item: " + userAlias);
            System.err.println(e.getMessage());
            return false;
        }
    }
}
