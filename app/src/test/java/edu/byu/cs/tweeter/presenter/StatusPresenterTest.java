package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.service.StatusService;
import edu.byu.cs.tweeter.model.service.StatusServiceProxy;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class StatusPresenterTest {

    private StatusRequest request;
    private StatusResponse response;
    private StatusServiceProxy mockStatusServiceProxy;
    private StatusPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        List<Status> statuses = new ArrayList<>();

        request = new StatusRequest("carterwonnacott", 10, null, true);
        response = new StatusResponse(statuses, false);

        // Create a mock StatusService
        mockStatusServiceProxy = Mockito.mock(StatusServiceProxy.class);
        Mockito.when(mockStatusServiceProxy.getStatuses(request)).thenReturn(response);

        // Wrap a StatusPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new StatusPresenter(new StatusPresenter.View() {
        }));
        Mockito.when(presenter.getStatusServiceProxy()).thenReturn(mockStatusServiceProxy);
    }

    @Test
    public void testStatus_returnsStatusResult() throws IOException {
        Mockito.when(mockStatusServiceProxy.getStatuses(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getStatus(request));
    }

    @Test
    public void testLogin_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockStatusServiceProxy.getStatuses(request)).thenThrow(new IOException());

        //doesn't throw for some reason
        Assertions.assertThrows(IOException.class, () -> presenter.getStatus(request));
    }
}
