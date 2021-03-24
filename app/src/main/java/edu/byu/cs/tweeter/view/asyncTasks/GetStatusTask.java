package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.presenter.StatusPresenter;

public class GetStatusTask extends AsyncTask<StatusRequest, Void, StatusResponse> {

    private final StatusPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void statusesRetrieved(StatusResponse statusResponse);
        void handleException(Exception exception);
    }

    public GetStatusTask(StatusPresenter presenter, GetStatusTask.Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected StatusResponse doInBackground(StatusRequest... statusRequests) {

        StatusResponse response = null;

        try {
            response = presenter.getStatus(statusRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(StatusResponse statusResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.statusesRetrieved(statusResponse);
        }
    }
}
