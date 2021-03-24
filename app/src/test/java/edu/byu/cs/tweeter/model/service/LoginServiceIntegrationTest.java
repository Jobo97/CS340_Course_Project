package edu.byu.cs.tweeter.model.service;

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

    LoginServiceProxy loginServiceProxy;

    LoginRequest loginRequest;
    LoginRequest invalidRequest;

    LoginResponse expectedResponse;
    LoginResponse invalidResponse;

    @BeforeEach
    public void setup() {
        loginRequest = new LoginRequest("carter", "wonnacott");
        invalidRequest = new LoginRequest(null, null);

        expectedResponse = new LoginResponse(new User("carter", "wonnacott", null), new AuthToken("authtoken"));
        invalidResponse = new LoginResponse("login failed");

        loginServiceProxy = new LoginServiceProxy();
    }

    @Test
    public void testLogin_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceProxy.login(loginRequest);
        Assertions.assertEquals(response, expectedResponse);
    }

    @Test
    public void testLogin_invalidRequest_incorrectResponse() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceProxy.login(invalidRequest);
        Assertions.assertEquals(response, invalidResponse);
    }

}
