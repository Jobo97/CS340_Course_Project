package edu.byu.cs.tweeter.model.service;

import android.util.Log;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LoginRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.LoginResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LoginServiceIntegrationTest {

    private final User ben = new User("Ben", "Millett", "@benmillett", MALE_IMAGE_URL);
    private final User michael = new User("Michael", "Skonnard", "@michaelskonnard", MALE_IMAGE_URL);
    private final User carter = new User("Carter", "Wonnacott", "@carterwonnacott", MALE_IMAGE_URL);
    private final User registeredUser = new User("firstname", "lastname", "username", MALE_IMAGE_URL);

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    LoginServiceProxy loginServiceProxy;

    LoginRequest loginRequest;
    LoginRequest registerRequest;
    LoginRequest invalidRequest;

    LoginResponse expectedResponse;
    LoginResponse registerResponse;
    LoginResponse invalidResponse;

    @BeforeEach
    public void setup() {
        loginRequest = new LoginRequest("carter", "wonnacott");
        registerRequest = new LoginRequest("someone", "new", "firstname", "lastname", null);
        invalidRequest = new LoginRequest(null, null);

        expectedResponse = new LoginResponse(carter, new AuthToken("Carter_Token"));
        registerResponse = new LoginResponse(registeredUser, new AuthToken("New_User"));
        invalidResponse = new LoginResponse(null,null);

        loginServiceProxy = new LoginServiceProxy();
    }

    @Test
    public void testLogin_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceProxy.login(loginRequest);
        boolean areEqual = response.getUser().equals(expectedResponse.getUser());
        areEqual = response.getAuthToken().equals(expectedResponse.getAuthToken()) && areEqual;
        Assertions.assertTrue(areEqual);
    }

    @Test
    public void testRegister_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceProxy.login(registerRequest);
        boolean areEqual = response.getUser().equals(registerResponse.getUser());
        areEqual = response.getAuthToken().equals(registerResponse.getAuthToken()) && areEqual;
        Assertions.assertTrue(areEqual);
    }

    @Test
    public void testLogin_invalidRequest_incorrectResponse() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceProxy.login(invalidRequest);
        boolean areEqual = response.getUser() == invalidResponse.getUser();
        areEqual = response.getAuthToken() == invalidResponse.getAuthToken() && areEqual;
        Assertions.assertTrue(areEqual);
    }

}
