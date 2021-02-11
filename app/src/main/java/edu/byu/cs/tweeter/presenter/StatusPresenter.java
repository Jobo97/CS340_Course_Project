package edu.byu.cs.tweeter.presenter;

import android.view.View;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.StatusService;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

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


    public StatusResponse getStatus(StatusRequest request) throws IOException {
        StatusService statusService = getStatusService();
        return statusService.getStatuses(request);
    }

    StatusService getStatusService() {
        return new StatusService();
    }
}
