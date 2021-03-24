package edu.byu.cs.tweeter.model.service;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.UserFollowResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.byu.cs.tweeter.model.net.ServerFacade_Old;

public class FollowServiceIntegrationTest {

    FollowServiceProxy followServiceProxy;

    FollowRequest followRequest;
    FollowRequest invalidFollowRequest;

    UserFollowRequest userFollowRequest;
    UserFollowRequest invalidUserFollowRequest;

    FollowCountRequest followCountRequest;
    FollowCountRequest invalidFollowCountRequest;

    FollowResponse expectedFollowResponse;
    FollowResponse invalidFollowResponse;

    UserFollowResponse expectedUserFollowResponse;
    UserFollowResponse invalidUserFollowResponse;

    FollowCountResponse expectedFollowCountResponse;
    FollowCountResponse invalidFollowCountResponse;

    @BeforeEach
    public void setup() {
        followRequest = new FollowRequest("carterwonnacott", 10, "michaelskonnard", true);
        invalidFollowRequest = new FollowRequest(null, 0, null, false);

        userFollowRequest = new UserFollowRequest("carterwonnacott", "michaelskonnard");
        invalidUserFollowRequest = new UserFollowRequest(null, null);

        followCountRequest = new FollowCountRequest("carterwonnacott");
        invalidFollowCountRequest = new FollowCountRequest(null);

        expectedFollowResponse = new FollowResponse(new ArrayList<>(), true);
        invalidFollowResponse = new FollowResponse("unable to fetch users");

        expectedUserFollowResponse = new UserFollowResponse(true, true);
        invalidUserFollowResponse = new UserFollowResponse(false, false);

        expectedFollowCountResponse = new FollowCountResponse(true, 3, 3);
        invalidFollowCountResponse = new FollowCountResponse(false, 0, 0);

        followServiceProxy = new FollowServiceProxy();
    }

    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = followServiceProxy.getFollows(followRequest);
        Assertions.assertEquals(expectedFollowResponse, response);
    }

    @Test
    public void testGetFollowees_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FollowResponse response = followServiceProxy.getFollows(followRequest);

        for(User user : response.getFollows()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }

    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowees() throws IOException, TweeterRemoteException {
        FollowResponse response = followServiceProxy.getFollows(invalidFollowRequest);
        Assertions.assertEquals(invalidFollowResponse, response);
    }

    @Test
    public void testGetFollowCount_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowCountResponse response = followServiceProxy.getFollowCount(followCountRequest);
        Assertions.assertEquals(expectedFollowCountResponse, response);
    }

    @Test
    public void testGetFollowCount_invalidRequest_returnsNoFollowees() throws IOException, TweeterRemoteException {
        FollowCountResponse response = followServiceProxy.getFollowCount(invalidFollowCountRequest);
        Assertions.assertEquals(invalidFollowCountResponse, response);
    }

    @Test
    public void testCheckFollow_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        UserFollowResponse response = followServiceProxy.checkFollow(userFollowRequest);
        Assertions.assertEquals(expectedUserFollowResponse, response);
    }

    @Test
    public void testCheckFollow_invalidRequest_returnsNoFollowees() throws IOException, TweeterRemoteException {
        UserFollowResponse response = followServiceProxy.checkFollow(invalidUserFollowRequest);
        Assertions.assertEquals(invalidUserFollowResponse, response);
    }

}
