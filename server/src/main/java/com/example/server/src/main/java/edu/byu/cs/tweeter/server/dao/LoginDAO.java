package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LoginRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.LoginResponse;

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
        User user;
        if (request.getRegistered()) {
            if (!userDAO.put(request)) return new LoginResponse("Register failed.");
        }
        else {
            //check username/password match
            Item item = userTable.getItem("user_alias", request.getUsername());
            if (request.getPassword() != item.getString("password"))
                return new LoginResponse("Login failed.");
        }

        PutItemOutcome outcome;
        try {
            //add authToken
            Map<String, Object> infoMap = new HashMap<>();
            infoMap.put("authtoken", generateAuthToken(request.getUsername()));
            outcome = table.putItem(new Item()
                    .withPrimaryKey("user_alias", request.getUsername())
                    .withMap("info", infoMap));
        } catch (Exception e) {
            System.out.println("AuthToken registration failed.");
            e.printStackTrace();
            return new LoginResponse("Login failed.");
        }

        user = userDAO.get(request.getUsername());
        Item item = outcome.getItem();
        AuthToken authToken = (AuthToken) item.get("authtoken");
        return new LoginResponse(user, authToken);
    }

    private AuthToken generateAuthToken(String username) {
        return new AuthToken(Integer.toString(username.hashCode()));
    }
}
