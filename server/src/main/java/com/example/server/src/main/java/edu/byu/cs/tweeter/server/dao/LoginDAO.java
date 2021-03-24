package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LoginRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class LoginDAO {

    private final User ben = new User("Ben", "Millett", "@benmillett", MALE_IMAGE_URL);
    private final User michael = new User("Michael", "Skonnard", "@michaelskonnard", MALE_IMAGE_URL);
    private final User carter = new User("Carter", "Wonnacott", "@carterwonnacott", MALE_IMAGE_URL);

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private User registeredUser;

    public LoginResponse login(LoginRequest request) {
        if(request.getUsername() == null || request.getPassword() == null){
            return new LoginResponse(null, null);
        }
        if (request.getRegistered()) {
            registeredUser = new User("firstname", "lastname", "username", MALE_IMAGE_URL);
            return new LoginResponse(registeredUser, new AuthToken("New_User"));
        }
        return new LoginResponse(carter, new AuthToken("Carter_Token"));
    }
}
