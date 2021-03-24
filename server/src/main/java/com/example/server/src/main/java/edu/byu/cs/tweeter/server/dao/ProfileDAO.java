package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;

import java.util.HashMap;
import java.util.Map;

public class ProfileDAO {

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User ben = new User("Ben", "Millett", "@benmillett", MALE_IMAGE_URL);
    private final User michael = new User("Michael", "Skonnard", "@michaelskonnard", MALE_IMAGE_URL);
    private final User carter = new User("Carter", "Wonnacott", "@carterwonnacott", MALE_IMAGE_URL);

    private Map<String, User> databaseUsernameUser = new HashMap<String, User>(){{
        put(ben.getAlias(), ben);
        put(michael.getAlias(), michael);
        put(carter.getAlias(), carter);
    }};

    public GetUserResponse getUser(GetUserRequest request) {
        if(databaseUsernameUser.get(request.getUseralias()) != null){
            GetUserResponse getUserResponse = new GetUserResponse(true, databaseUsernameUser.get(request.getUseralias()));
            return getUserResponse;
        }
        return new GetUserResponse(false,null);
    }
}
