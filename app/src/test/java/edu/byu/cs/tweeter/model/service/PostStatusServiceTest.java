package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.Response;

public class PostStatusServiceTest {

    private PostStatusRequest validRequest;
    private PostStatusRequest invalidRequest;

    private Response successResponse;
    private Response failureResponse;

    private PostStatusService followServiceSpy;

    /**
     * Create a PostStatusService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        // Setup request objects to use in the tests
        validRequest = new PostStatusRequest("test tweet", "test alias");
        invalidRequest = new PostStatusRequest(null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new Response(true);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.postStatus(validRequest)).thenReturn(successResponse);

        failureResponse = new Response(false);
        Mockito.when(mockServerFacade.postStatus(invalidRequest)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        followServiceSpy = Mockito.spy(new PostStatusService());
        Mockito.when(followServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link PostStatusService#postStatus(PostStatusRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     */
    @Test
    public void testPostStatus_validRequest_correctResponse() {
        Response response = followServiceSpy.postStatus(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link PostStatusService#postStatus(PostStatusRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     */
    @Test
    public void testPostStatus_invalidRequest_returnsNoUser() {
        Response response = followServiceSpy.postStatus(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}