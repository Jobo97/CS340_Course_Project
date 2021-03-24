package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.ServerFacade_Old;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;

public class ProfileServiceTest {

    private GetUserRequest validRequest;
    private GetUserRequest invalidRequest;

    private GetUserResponse successResponse;
    private GetUserResponse failureResponse;

    private String url;

    private ProfileServiceProxy followServiceSpy;

    /**
     * Create a LoginService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        // Setup request objects to use in the tests
        validRequest = new GetUserRequest("test-alias");
        invalidRequest = new GetUserRequest(null);
        url = "/profile";

        // Setup a mock ServerFacade that will return known responses
        successResponse = new GetUserResponse(true, currentUser);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getUser(validRequest, url)).thenReturn(successResponse);

        failureResponse = new GetUserResponse(false, null);
        Mockito.when(mockServerFacade.getUser(invalidRequest, url)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        followServiceSpy = Mockito.spy(new ProfileServiceProxy());
        Mockito.when(followServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link ProfileService#getUser(GetUserRequest)}
     * method returns the same result as the {@link ServerFacade_Old}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetUser_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        GetUserResponse response = followServiceSpy.getUser(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link ProfileService#getUser(GetUserRequest)}
     * method returns the same result as the {@link ServerFacade_Old}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetUser_invalidRequest_returnsNoUser() throws IOException, TweeterRemoteException {
        GetUserResponse response = followServiceSpy.getUser(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}