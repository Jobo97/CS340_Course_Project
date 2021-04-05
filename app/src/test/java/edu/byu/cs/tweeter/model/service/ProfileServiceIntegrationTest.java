package edu.byu.cs.tweeter.model.service;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfileServiceIntegrationTest {

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

    ProfileServiceProxy profileServiceProxy;

    GetUserRequest getUserRequest;
    GetUserRequest invalidRequest;

    GetUserResponse expectedResponse;
    GetUserResponse invalidResponse;

    @BeforeEach
    public void setup() {
        getUserRequest = new GetUserRequest("@carterwonnacott");
        invalidRequest = new GetUserRequest(null);

        expectedResponse = new GetUserResponse(true, carter);
        invalidResponse = new GetUserResponse(false, null);

        profileServiceProxy = new ProfileServiceProxy();
    }

    @Test
    public void testGetUser_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        GetUserResponse response = profileServiceProxy.getUser(getUserRequest);
        boolean areEqual = expectedResponse.isSuccess() == response.isSuccess();
        areEqual = expectedResponse.getViewedUser().equals(response.getViewedUser()) && areEqual;
        Assertions.assertTrue(areEqual);
    }

    @Test
    public void testGetUser_invalidRequest_incorrectResponse() throws IOException, TweeterRemoteException {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            profileServiceProxy.getUser(invalidRequest);
        });
        String actualMessage = exception.getMessage();
        Assertions.assertNull(actualMessage);
    }
}
