package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.presenter.MainPresenter;

public class PostStatusTask extends AsyncTask<PostStatusRequest, Void, Response> {

    private final MainPresenter presenter;
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

    public PostStatusTask(MainPresenter presenter, PostStatusTask.Observer observer) {
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

        } catch (IOException | TweeterRemoteException | RuntimeException ex) {
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
