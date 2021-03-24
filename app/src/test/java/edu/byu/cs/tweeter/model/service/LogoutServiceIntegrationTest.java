package edu.byu.cs.tweeter.model.service;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LogoutServiceIntegrationTest {

    LogoutServiceProxy logoutServiceProxy;

    LogoutRequest logoutRequest;
    LogoutRequest invalidRequest;

    Response expectedResponse;
    Response invalidResponse;

    @BeforeEach
    public void setup() {
        logoutRequest = new LogoutRequest("carterwonnacott", new AuthToken("authtoken"));
        invalidRequest = new LogoutRequest(null, null);

        expectedResponse = new Response(true);
        invalidResponse = new Response(false);

        logoutServiceProxy = new LogoutServiceProxy();
    }

    @Test
    public void testLogout_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        Response response = logoutServiceProxy.logout(logoutRequest);
        Assertions.assertEquals(response.isSuccess(), expectedResponse.isSuccess());
    }

    @Test
    public void testLogout_invalidRequest_incorrectResponse() throws IOException, TweeterRemoteException {
        Response response = logoutServiceProxy.logout(invalidRequest);
        Assertions.assertEquals(response.isSuccess(), invalidResponse.isSuccess());
    }
}
