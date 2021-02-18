package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.presenter.PostStatusPresenter;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class PostStatusTask extends AsyncTask<PostStatusRequest, Void, Response> {

    private final PostStatusPresenter presenter;
    private final PostStatusTask.Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void postSuccessful(Response response);
        void postUnsuccessful(Response response);
        void handleException(Exception ex);
    }

    public PostStatusTask(PostStatusPresenter presenter, PostStatusTask.Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected Response doInBackground(PostStatusRequest... postStatusRequests) {
        Response response = null;

        try {
            response = presenter.postStatus(postStatusRequests[0]);

        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(Response response) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(response.isSuccess()) {
            observer.postSuccessful(response);
        } else {
            observer.postUnsuccessful(response);
        }
    }


}
