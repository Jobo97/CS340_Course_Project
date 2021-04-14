package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LoginRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.LoginResponse;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

public class LoginDAO {

//    private final User ben = new User("Ben", "Millett", "@benmillett", MALE_IMAGE_URL);
//    private final User michael = new User("Michael", "Skonnard", "@michaelskonnard", MALE_IMAGE_URL);
//    private final User carter = new User("Carter", "Wonnacott", "@carterwonnacott", MALE_IMAGE_URL);
//
//    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
//    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";
    AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
        .standard()
        .withRegion("us-west-2")
        .build();

    private DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
    private Table table = dynamoDB.getTable("authtoken");
    private Table userTable = dynamoDB.getTable("user");

    private UserDAO userDAO = new UserDAO();

    public LoginResponse login(LoginRequest request) {

        System.out.println(request);
        User user;
        if (request.getRegistered()) {
            if (!userDAO.put(request)) return new LoginResponse("Register failed.");
        }
        else {
            //check username/password match
            //GetItemSpec
            Item item = userTable.getItem("user_alias", request.getUsername());
            if (item != null) {
                System.out.println("PASSWORD STUFF");
                System.out.println("Request password:");
                System.out.println(request.getPassword());
                System.out.println("item.getString(\"password\"):");
                System.out.println(item.getString("password"));

                String unhashedPassword = item.getString("password");

                boolean matched = false;
                try {
                    matched = PasswordHasher.validatePassword(request.getPassword(), unhashedPassword);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }
                System.out.println("Request password:");
                System.out.println(request.getPassword());
                System.out.println("unhashed password:");

                System.out.println(unhashedPassword);
                if (!matched) {
                    return new LoginResponse("Login failed: Invalid password");
                }
            }
            else {
                return new LoginResponse("Login failed: Invalid username.");
            }
        }

        PutItemOutcome outcome;
        String authToken;
        try {
            AuthTokenDAO authTokenDAO = new AuthTokenDAO();
            AuthToken token = generateAuthToken(request.getUsername());
            authTokenDAO.put(request.getUsername(), token.getAuthToken());
            authToken = token.getAuthToken();
        } catch (Exception e) {
            System.out.println("AuthToken registration failed.");
            e.printStackTrace();
            return new LoginResponse("Login failed.");
        }
        user = userDAO.get(request.getUsername());

        // item probably null here

        System.out.println("ITEM INFO FROM USER TABLE");
        System.out.println(authToken);
        System.out.println(user.getAlias());

        AuthToken a = new AuthToken();
        a.setAuthToken(authToken);

        // Create some sort of timestamp an place it into the table

        return new LoginResponse(user, a);
    }

    private AuthToken generateAuthToken(String userName) {
        return new AuthToken(Integer.toString(userName.hashCode()));
    }
}

//            //add authToken
////            Map<String, Object> infoMap = new HashMap<>();
//            System.out.println("Reaches 68");
//            Item item = new Item();
//            // Create some sort of timestamp an place it into the table
//            item.withPrimaryKey("user_alias", request.getUsername())
//                    .withString("authtoken", generateAuthToken(request.getUsername()).getAuthToken());
////            infoMap.put("authtoken", (generateAuthToken(request.getPassword())).getAuthToken());
////            outcome = table.putItem(new Item()
////                    .withPrimaryKey("user_alias", request.getUsername())
////                    .withMap("info", infoMap));
//            PutItemSpec putItemSpec = new PutItemSpec()
//                    .withItem(item)
//                    .withReturnValues(ReturnValue.ALL_OLD);
//            System.out.println("Reaches 79");