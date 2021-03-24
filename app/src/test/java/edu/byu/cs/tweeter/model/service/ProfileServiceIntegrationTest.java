package edu.byu.cs.tweeter.model.service;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ProfileServiceIntegrationTest {

    ProfileServiceProxy profileServiceProxy;

    GetUserRequest getUserRequest;
    GetUserRequest invalidRequest;

    GetUserResponse expectedResponse;
    GetUserResponse invalidResponse;

    @BeforeEach
    public void setup() {
        getUserRequest = new GetUserRequest("carterwonnacott");
        invalidRequest = new GetUserRequest(null);

        expectedResponse = new GetUserResponse(true, new User("carter", "wonnacott", null));
        invalidResponse = new GetUserResponse(false, null);

        profileServiceProxy = new ProfileServiceProxy();
    }

    @Test
    public void testGetUser_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        GetUserResponse response = profileServiceProxy.getUser(getUserRequest);
        Assertions.assertEquals(response, expectedResponse);
    }

    @Test
    public void testGetUser_invalidRequest_incorrectResponse() throws IOException, TweeterRemoteException {
        GetUserResponse response = profileServiceProxy.getUser(invalidRequest);
        Assertions.assertEquals(response, invalidResponse);
    }
}
