package com.example.server.src.test.java.edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.FollowServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.FollowDAO;

public class FollowServiceImplTest {

    private FollowRequest request;
    private FollowResponse expectedResponse;
    private FollowDAO mockFollowDAO;
    private FollowServiceImpl followingServiceImplSpy;

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
        mockFollowDAO = Mockito.mock(FollowDAO.class);
        Mockito.when(mockFollowDAO.getFollows(request)).thenReturn(expectedResponse);

        followingServiceImplSpy = Mockito.spy(FollowServiceImpl.class);
        Mockito.when(followingServiceImplSpy.getFollowingDAO()).thenReturn(mockFollowDAO);
    }

    /**
     * Verify that the getFollowees(FollowingRequest)}
     * method returns the same result as the {@link FollowDAO} class.
     */
    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = followingServiceImplSpy.getFollows(request);
        Assertions.assertEquals(expectedResponse, response);
    }
}
