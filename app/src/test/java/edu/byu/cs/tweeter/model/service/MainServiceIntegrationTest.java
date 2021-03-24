package edu.byu.cs.tweeter.model.service;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class MainServiceIntegrationTest {

    MainServiceProxy mainServiceProxy;

    PostStatusRequest postStatusRequest;
    PostStatusRequest invalidRequest;

    Response expectedResponse;
    Response invalidResponse;

    @BeforeEach
    public void setup() {
        postStatusRequest = new PostStatusRequest("tweet", "@carterwonnacott");
        invalidRequest = new PostStatusRequest(null, null);

        expectedResponse = new Response(true);
        invalidResponse = new Response(false);

        mainServiceProxy = new MainServiceProxy();
    }

    @Test
    public void testPostStatus_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        Response response = mainServiceProxy.postStatus(postStatusRequest);
        Assertions.assertEquals(response, expectedResponse);
    }

    @Test
    public void testPostStatus_invalidRequest_incorrectResponse() throws IOException, TweeterRemoteException {
        Response response = mainServiceProxy.postStatus(invalidRequest);
        Assertions.assertEquals(response, invalidResponse);
    }

}
