package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.UserFollowResponse;

public class FollowServiceTest {

    private FollowRequest validRequest;
    private FollowRequest invalidRequest;

    private FollowResponse successResponse;
    private FollowResponse failureResponse;

    private FollowCountRequest validCount;
    private FollowCountRequest invalidCount;

    private FollowCountResponse successCount;
    private FollowCountResponse failureCount;

    private UserFollowRequest validFollow;
    private UserFollowRequest invalidFollow;

    private UserFollowResponse successFollow;
    private UserFollowResponse failureFollow;

    private FollowService followServiceSpy;

    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        // Setup request objects to use in the tests
        validRequest = new FollowRequest(currentUser.getAlias(), 3, null, true);
        invalidRequest = new FollowRequest(null, 0, null, true);

        validCount = new FollowCountRequest(currentUser.getAlias());
        invalidCount = new FollowCountRequest(null);

        validFollow = new UserFollowRequest(currentUser.getAlias(), resultUser1.getAlias());
        invalidFollow = new UserFollowRequest(null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FollowResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFollows(validRequest)).thenReturn(successResponse);

        failureResponse = new FollowResponse("An exception occurred");
        Mockito.when(mockServerFacade.getFollows(invalidRequest)).thenReturn(failureResponse);

        successCount = new FollowCountResponse(true, 10, 10);
        Mockito.when(mockServerFacade.getFollowCount(validCount)).thenReturn(successCount);

        failureCount = new FollowCountResponse(false, 0, 0);
        Mockito.when(mockServerFacade.getFollowCount(invalidCount)).thenReturn(failureCount);

        successFollow = new UserFollowResponse(true, true);
        Mockito.when(mockServerFacade.checkFollows(validFollow)).thenReturn(successFollow);
        Mockito.when(mockServerFacade.followStatus(validFollow)).thenReturn(successFollow);

        failureFollow = new UserFollowResponse(false, false);
        Mockito.when(mockServerFacade.checkFollows(invalidFollow)).thenReturn(failureFollow);
        Mockito.when(mockServerFacade.followStatus(invalidFollow)).thenReturn(failureFollow);
        
        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        followServiceSpy = Mockito.spy(new FollowService());
        Mockito.when(followServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link FollowService#getFollows(FollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException {
        FollowResponse response = followServiceSpy.getFollows(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link FollowService#getFollows(FollowRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_validRequest_loadsProfileImages() throws IOException {
        FollowResponse response = followServiceSpy.getFollows(validRequest);

        for(User user : response.getFollows()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }

    /**
     * Verify that for failed requests the {@link FollowService#getFollows(FollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowees() throws IOException {
        FollowResponse response = followServiceSpy.getFollows(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

    /**
     * Verify that for successful requests the {@link FollowService#getFollowCount(FollowCountRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowCount_validRequest_correctResponse() throws IOException {
        FollowCountResponse response = followServiceSpy.getFollowCount(validCount);
        Assertions.assertEquals(successCount, response);
    }

    /**
     * Verify that for failed requests the {@link FollowService#getFollowCount(FollowCountRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowCount_invalidRequest_returnsNoFollowees() throws IOException {
        FollowCountResponse response = followServiceSpy.getFollowCount(invalidCount);
        Assertions.assertEquals(failureCount, response);
    }

    /**
     * Verify that for successful requests the {@link FollowService#checkFollow(UserFollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testCheckFollow_validRequest_correctResponse() throws IOException {
        UserFollowResponse response = followServiceSpy.checkFollow(validFollow);
        Assertions.assertEquals(successFollow, response);
    }

    /**
     * Verify that for failed requests the {@link FollowService#checkFollow(UserFollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testCheckFollow_invalidRequest_returnsNoFollowees() throws IOException {
        UserFollowResponse response = followServiceSpy.checkFollow(invalidFollow);
        Assertions.assertEquals(failureFollow, response);
    }

}
