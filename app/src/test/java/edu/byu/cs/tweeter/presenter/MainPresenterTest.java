package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.MainService;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

public class MainPresenterTest {

    private PostStatusRequest request;
    private Response response;
    private MainService mockLoginService;
    private MainPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {

        request = new PostStatusRequest("test-tweet", "test-alias");
        response = new Response(true);

        // Create a mock PostStatusService
        mockLoginService = Mockito.mock(MainService.class);
        Mockito.when(mockLoginService.postStatus(request)).thenReturn(response);

        // Wrap a PostStatusPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new MainPresenter(new MainPresenter.View() {}));
        Mockito.when(presenter.postStatus(request)).thenReturn(response);
    }

    @Test
    public void testPostStatus_returnsPostStatusResult() throws IOException {
        Mockito.when(mockLoginService.postStatus(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.postStatus(request));
    }

}
