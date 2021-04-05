package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.StatusService;
import edu.byu.cs.tweeter.model.service.StatusServiceProxy;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class StatusPresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }


    public StatusPresenter(View view) {
        this.view = view;
    }


    public StatusResponse getStatus(StatusRequest request) throws Exception, RuntimeException {
        StatusServiceProxy statusServiceProxy = getStatusServiceProxy();
        return statusServiceProxy.getStatuses(request);
    }

    StatusServiceProxy getStatusServiceProxy() {
        return new StatusServiceProxy();
    }
}
