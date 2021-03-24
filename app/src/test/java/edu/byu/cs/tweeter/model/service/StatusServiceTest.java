package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.ServerFacade_Old;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class StatusServiceTest {

    private StatusRequest validRequest;
    private StatusRequest invalidRequest;

    private StatusResponse successResponse;
    private StatusResponse failureResponse;

    private StatusServiceProxy followServiceSpy;
    private ServerFacade mockServerFacade;
    private String url;



    /**
     * Create a StatusService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        url = "/statuses";

        // Setup request objects to use in the tests
        validRequest = new StatusRequest("test alias", 10, null, true);
        invalidRequest = new StatusRequest(null, 0, null, false);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new StatusResponse("success");
        mockServerFacade = Mockito.mock(ServerFacade.class);

        failureResponse = new StatusResponse("failure");

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        followServiceSpy = Mockito.spy(StatusServiceProxy.class);
        Mockito.when(followServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link StatusService#getStatuses(StatusRequest)}
     * method returns the same result as the {@link ServerFacade_Old}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStatuses_validRequest_correctResponse() throws Exception {
        Mockito.when(mockServerFacade.getStatuses(validRequest, url)).thenReturn(successResponse);
        Response response = followServiceSpy.getStatuses(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link StatusService#getStatuses(StatusRequest)}
     * method returns the same result as the {@link ServerFacade_Old}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStatuses_invalidRequest_returnsNoUser() throws Exception {
        Mockito.when(mockServerFacade.getStatuses(invalidRequest, url)).thenReturn(failureResponse);
        Response response = followServiceSpy.getStatuses(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}