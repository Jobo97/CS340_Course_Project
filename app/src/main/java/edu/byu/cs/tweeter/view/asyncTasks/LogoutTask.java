package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.presenter.LogoutPresenter;

public class LogoutTask extends AsyncTask<LogoutRequest, Void, Response> {

private final LogoutPresenter presenter;
private final Observer observer;
private Exception exception;

/**
 * An observer interface to be implemented by observers who want to be notified when this task
 * completes.
 */
public interface Observer {
    void logoutSuccessful(Response response);

    void logoutUnsuccessful(Response response);

    void handleException(Exception ex);

}

    /**
     * Creates an instance.
     *
     * @param presenter the presenter this task should use to login.
     * @param observer  the observer who wants to be notified when this task completes.
     */
    public LogoutTask(LogoutPresenter presenter, LogoutTask.Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected Response doInBackground(LogoutRequest... logoutRequests) {
        Response response = null;

        try {
            response = presenter.logout(logoutRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(Response response) {
        if (exception != null) {
            observer.handleException(exception);
        } else if (response.isSuccess()) {
            observer.logoutSuccessful(response);
        } else {
            observer.logoutUnsuccessful(response);
        }
    }
}
