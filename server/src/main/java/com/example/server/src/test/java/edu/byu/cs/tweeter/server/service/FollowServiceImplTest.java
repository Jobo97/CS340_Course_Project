package com.example.server.src.test.java.edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.FollowDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.FollowServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.FollowDAOM3;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.UserFollowResponse;

public class FollowServiceImplTest {

    private FollowRequest request;
    private FollowResponse expectedResponse;
    private FollowDAO mMockFollowDAO;
    private FollowServiceImpl followingServiceImplSpy;

    private UserFollowRequest userFollowRequest;
    private UserFollowResponse userFollowResponse;

    private FollowCountRequest followCountRequest;
    private FollowCountResponse followCountResponse;
    private FollowRequest invalidRequest;
    private FollowResponse invalidResponse;
    private UserFollowRequest invalidUserFollowRequest;
    private UserFollowResponse invalidUserFollowResponse;
    private FollowCountRequest invalidFollowCountRequest;
    private FollowCountResponse invalidFollowCountResponse;


    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        // Setup a request object to use in the tests
        request = new FollowRequest(currentUser.getAlias(), 3, null, true);

        // Setup a mock FollowingDAO that will return known responses
        expectedResponse = new FollowResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        invalidRequest = new FollowRequest(null, 3, null, true);
        invalidResponse = new FollowResponse(null, false);

        userFollowRequest = new UserFollowRequest(currentUser.getAlias(), resultUser1.getAlias());
        userFollowResponse = new UserFollowResponse(true, true);
        invalidUserFollowRequest = new UserFollowRequest(null, null);
        invalidUserFollowResponse = new UserFollowResponse(false, false);

        followCountRequest = new FollowCountRequest(currentUser.getAlias());
        followCountResponse = new FollowCountResponse(true, 100, 100);
        invalidFollowCountRequest = new FollowCountRequest(null);
        invalidFollowCountResponse = new FollowCountResponse(false, -1, -1);

        followingServiceImplSpy = Mockito.spy(FollowServiceImpl.class);
        mMockFollowDAO = Mockito.mock(FollowDAO.class);
        Mockito.when(followingServiceImplSpy.getFollowingDAO()).thenReturn(mMockFollowDAO);
        AuthTokenDAO authTokenDAO = Mockito.mock(AuthTokenDAO.class);
        Mockito.when(followingServiceImplSpy.getAuthTokenDAO()).thenReturn(authTokenDAO);
        Mockito.when(authTokenDAO.validateSession(currentUser.getAlias())).thenReturn(true);
        Mockito.when(authTokenDAO.validateSession(null)).thenReturn(true);
    }

    /**
     * Verify that the getFollowees(FollowingRequest)}
     * method returns the same result as the {@link FollowDAOM3} class.
     */
    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        Mockito.when(mMockFollowDAO.getFollows(request)).thenReturn(expectedResponse);
        FollowResponse response = followingServiceImplSpy.getFollows(request);
        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    public void testGetFollowees_invalidRequest_incorrectResponse() throws IOException, TweeterRemoteException {
        Mockito.when(mMockFollowDAO.getFollows(invalidRequest)).thenReturn(invalidResponse);
        FollowResponse response = followingServiceImplSpy.getFollows(invalidRequest);
        Assertions.assertEquals(invalidResponse, response);
    }


    @Test
    public void testCheckFollow_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        Mockito.when(mMockFollowDAO.checkFollows(userFollowRequest)).thenReturn(userFollowResponse);
        UserFollowResponse response = followingServiceImplSpy.checkFollow(userFollowRequest);
        Assertions.assertEquals(userFollowResponse, response);
    }

    @Test
    public void testCheckFollow_invalidRequest_incorrectResponse() throws IOException, TweeterRemoteException {
        Mockito.when(mMockFollowDAO.checkFollows(invalidUserFollowRequest)).thenReturn(invalidUserFollowResponse);
        UserFollowResponse response = followingServiceImplSpy.checkFollow(invalidUserFollowRequest);
        Assertions.assertEquals(invalidUserFollowResponse, response);
    }


    @Test
    public void testGetFollowCount_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        Mockito.when(mMockFollowDAO.getFollowCount(followCountRequest)).thenReturn(followCountResponse);
        FollowCountResponse response = followingServiceImplSpy.getFollowCount(followCountRequest);
        Assertions.assertEquals(followCountResponse, response);
    }

    @Test
    public void testGetFollowCount_invalidRequest_incorrectResponse() throws IOException, TweeterRemoteException {
        Mockito.when(mMockFollowDAO.getFollowCount(invalidFollowCountRequest)).thenReturn(invalidFollowCountResponse);
        FollowCountResponse response = followingServiceImplSpy.getFollowCount(invalidFollowCountRequest);
        Assertions.assertEquals(invalidFollowCountResponse, response);
    }


    @Test
    public void testFollowStatus_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        Mockito.when(mMockFollowDAO.followStatus(userFollowRequest)).thenReturn(userFollowResponse);
        UserFollowResponse response = followingServiceImplSpy.followStatus(userFollowRequest);
        Assertions.assertEquals(userFollowResponse, response);
    }

    @Test
    public void testFollowStatus_invalidRequest_incorrectResponse() throws IOException, TweeterRemoteException {
        Mockito.when(mMockFollowDAO.followStatus(invalidUserFollowRequest)).thenReturn(invalidUserFollowResponse);
        UserFollowResponse response = followingServiceImplSpy.followStatus(invalidUserFollowRequest);
        Assertions.assertEquals(invalidUserFollowResponse, response);
    }

}
