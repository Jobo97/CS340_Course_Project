package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.LoginServiceProxy;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LoginRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class LoginPresenterTest {

    private LoginRequest request;
    private LoginResponse response;
    private LoginServiceProxy mockLoginServiceProxy;
    private LoginPresenter presenter;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        request = new LoginRequest("test-username", "test-password");
        response = new LoginResponse(currentUser, new AuthToken("test-authtoken"));

        // Create a mock LoginService
        mockLoginServiceProxy = Mockito.mock(LoginServiceProxy.class);

        // Wrap a LoginPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new LoginPresenter(new LoginPresenter.View() {}));
        Mockito.when(presenter.getLoginServiceProxy()).thenReturn(mockLoginServiceProxy);
    }

    @Test
    public void testLogin_returnsLoginResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockLoginServiceProxy.login(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.login(request));
    }

    @Test
    public void testLogin_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockLoginServiceProxy.login(request)).thenThrow(new IOException());

        // doesn't throw for some reason
        Assertions.assertThrows(IOException.class, () -> presenter.login(request));
    }
}
