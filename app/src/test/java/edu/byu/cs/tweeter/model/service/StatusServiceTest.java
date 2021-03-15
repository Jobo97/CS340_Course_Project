package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class StatusServiceTest {

    private StatusRequest validRequest;
    private StatusRequest invalidRequest;

    private StatusResponse successResponse;
    private StatusResponse failureResponse;

    private StatusService followServiceSpy;

    /**
     * Create a StatusService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        // Setup request objects to use in the tests
        validRequest = new StatusRequest("test alias", 10, null, true);
        invalidRequest = new StatusRequest(null, 0, null, false);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new StatusResponse("success");
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getStatuses(validRequest)).thenReturn(successResponse);

        failureResponse = new StatusResponse("failure");
        Mockito.when(mockServerFacade.getStatuses(invalidRequest)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        followServiceSpy = Mockito.spy(new StatusService());
        Mockito.when(followServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link StatusService#getStatuses(StatusRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStatuses_validRequest_correctResponse() throws IOException {
        Response response = followServiceSpy.getStatuses(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link StatusService#getStatuses(StatusRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStatuses_invalidRequest_returnsNoUser() throws IOException {
        Response response = followServiceSpy.getStatuses(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}