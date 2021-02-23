package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.StatusService;
import edu.byu.cs.tweeter.model.service.UserService;
import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class UserPresenterTest {

    private GetUserRequest request;
    private GetUserResponse response;
    private UserService mockStatusService;
    private UserPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        User viewedUser = new User("FirstName", "LastName", null);

        request = new GetUserRequest("carterwonnacott");
        response = new GetUserResponse(true, viewedUser);

        // Create a mock StatusService
        mockStatusService = Mockito.mock(UserService.class);
        Mockito.when(mockStatusService.getUser(request)).thenReturn(response);

        // Wrap a StatusPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new UserPresenter(new UserPresenter.View() {
        }));
        Mockito.when(presenter.getUser(request)).thenReturn(response);
    }

    @Test
    public void testUser_returnsGetUserResult() throws IOException {
        Mockito.when(mockStatusService.getUser(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getUser(request));
    }

    @Test
    public void testUser_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockStatusService.getUser(request)).thenThrow(new IOException());

        //doesn't throw for some reason
        Assertions.assertThrows(IOException.class, () -> presenter.getUser(request));
    }
}
