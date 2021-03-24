package edu.byu.cs.tweeter.model.service;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class StatusServiceIntegrationTest {

    StatusServiceProxy statusServiceProxy;

    StatusRequest statusRequest;
    StatusRequest invalidRequest;

    StatusResponse expectedResponse;
    StatusResponse invalidResponse;

    @BeforeEach
    public void setup() {
        // setup request
        statusRequest = new StatusRequest("@carterwonnacott", 10, "", false);
        invalidRequest = new StatusRequest("", 0, "", false);
        // setup expected response
        expectedResponse = new StatusResponse(new ArrayList<>(), true);
        invalidResponse = new StatusResponse(null, false);
        // setup service
        statusServiceProxy = new StatusServiceProxy();
    }

    @Test
    public void testGetStatuses_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        StatusResponse response = statusServiceProxy.getStatuses(statusRequest);
        Assertions.assertEquals(response, expectedResponse);
    }

    @Test
    public void testGetStatuses_invalidRequest_incorrectResponse() throws IOException, TweeterRemoteException {
        StatusResponse response = statusServiceProxy.getStatuses(invalidRequest);
        Assertions.assertEquals(response, invalidResponse);
    }

}
