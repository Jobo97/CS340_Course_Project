package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.UserFollowResponse;

import java.util.HashMap;
import java.util.Map;

public class ProfileDAO {

//    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
//    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";
//
//    private final User ben = new User("Ben", "Millett", "@benmillett", MALE_IMAGE_URL);
//    private final User michael = new User("Michael", "Skonnard", "@michaelskonnard", MALE_IMAGE_URL);
//    private final User carter = new User("Carter", "Wonnacott", "@carterwonnacott", MALE_IMAGE_URL);
//
//    private Map<String, User> databaseUsernameUser = new HashMap<String, User>(){{
//        put(ben.getAlias(), ben);
//        put(michael.getAlias(), michael);
//        put(carter.getAlias(), carter);
//    }};

//    AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
//            .standard()
//            .withRegion("us-west-2")
//            .build();
//
//    private DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
//    private Table table = dynamoDB.getTable("user");
    private UserDAO uDao = new UserDAO();

    public GetUserResponse getUser(GetUserRequest request) {
//        if(databaseUsernameUser.get(request.getUseralias()) != null){
//            GetUserResponse getUserResponse = new GetUserResponse(true, databaseUsernameUser.get(request.getUseralias()));
//            return getUserResponse;
//        }
//        return new GetUserResponse(false,null);
        try {
            User user = uDao.get(request.getUseralias());
            if(user != null){
                return new GetUserResponse(true, user);
            }
            else {
                return new GetUserResponse(true, null);
            }
        }
        catch (Exception e) {
            System.err.println("Unable to get " + request.getUseralias());
            System.err.println(e.getMessage());
            return new GetUserResponse(false, null);
        }

    }
}
