package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.ProfileService;
import edu.byu.cs.tweeter.model.service.ProfileServiceProxy;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;

public class ProfilePresenterTest {

    private GetUserRequest request;
    private GetUserResponse response;
    private ProfileServiceProxy mockProfileServiceProxy;
    private ProfilePresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User viewedUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        request = new GetUserRequest("@carterwonnacott");
        response = new GetUserResponse(true, viewedUser);

        // Create a mock StatusService
        mockProfileServiceProxy = Mockito.mock(ProfileServiceProxy.class);
        Mockito.when(mockProfileServiceProxy.getUser(request)).thenReturn(response);

        // Wrap a StatusPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new ProfilePresenter(new ProfilePresenter.View() {
        }));
        Mockito.when(presenter.getProfileServiceProxy()).thenReturn(mockProfileServiceProxy);
    }

    @Test
    public void testUser_returnsGetUserResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockProfileServiceProxy.getUser(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getUser(request));
    }

    @Test
    public void testUser_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockProfileServiceProxy.getUser(request)).thenThrow(new IOException());

        //doesn't throw for some reason
        Assertions.assertThrows(IOException.class, () -> presenter.getUser(request));
    }
}
