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

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
    S3DAO s3DAO = new S3DAO();

    public boolean put(LoginRequest request) {

//        final Map<String, Object> infoMap = new HashMap<String, Object>();
//        infoMap.put("username", request.getUsername());
//        infoMap.put("password", request.getPassword());
//        infoMap.put("firstName", request.getFirstname());
//        infoMap.put("lastName", request.getLastname());
          //infoMap.put("imageEncoded", followee_name);


        // put user's image into bucket
        s3DAO.putObject(request.getImageBytes(), request.getUsername());

        String hashedPassword;
        try {
            hashedPassword = PasswordHasher.generateStrongPasswordHash(request.getPassword());
        }  catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            hashedPassword = request.getPassword();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            hashedPassword = request.getPassword();
        }

        try {
            PutItemOutcome outcome = table
                    .putItem(new Item()
                            .withPrimaryKey("user_alias", request.getUsername())
                            .withString("password", hashedPassword)
                            .withString("firstName", request.getFirstname())
                            .withString("lastName", request.getLastname()));
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
            if (outcome.getString("imageUrl") != null) {
                u.setImageUrl(outcome.getString("imageUrl"));
            }
            else {
                u.setImageUrl(s3DAO.getObject(u.getAlias()));
            }
            //u.setImageBytes(s3DAO.getObject(u.getAlias()));

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
}
