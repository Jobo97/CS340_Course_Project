package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.LogoutServiceProxy;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

public class LogoutPresenterTest {

    private LogoutRequest request;
    private Response response;
    private LogoutServiceProxy mockLogoutServiceProxy;
    private LogoutPresenter presenter;

    @BeforeEach
    public void setup() {
        request = new LogoutRequest("test-username", new AuthToken("test-token"));
        response = new Response(true, "logout successful");

        // Create a mock LoginService
        mockLogoutServiceProxy = Mockito.mock(LogoutServiceProxy.class);

        // Wrap a LoginPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new LogoutPresenter(new LogoutPresenter.View() {}));
        Mockito.when(presenter.getLogoutService()).thenReturn(mockLogoutServiceProxy);
    }

    @Test
    public void testLogin_returnsLoginResult() throws IOException {
        Mockito.when(mockLogoutServiceProxy.logout(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.logout(request));
    }

    @Test
    public void testLogin_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockLogoutServiceProxy.logout(request)).thenThrow(new IOException());

        // doesn't throw for some reason
        Assertions.assertThrows(IOException.class, () -> presenter.logout(request));
    }
}
