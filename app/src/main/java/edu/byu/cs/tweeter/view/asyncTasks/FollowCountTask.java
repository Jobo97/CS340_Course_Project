package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import edu.byu.cs.tweeter.presenter.FollowPresenter;

public class FollowCountTask extends AsyncTask<FollowCountRequest, Void, FollowCountResponse> {

    private final FollowPresenter presenter;
    private final FollowCountTask.Observer observer;
    private Exception exception;

    public interface Observer {
        void followCount(FollowCountResponse followCountResponse);
        void handleException(Exception exception);
    }

    public FollowCountTask(FollowPresenter presenter, FollowCountTask.Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FollowCountResponse doInBackground(FollowCountRequest... followCountRequests) {

        FollowCountResponse response = null;

        try {
            response = presenter.getFollowCount(followCountRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(FollowCountResponse followCountResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.followCount(followCountResponse);
        }
    }
}