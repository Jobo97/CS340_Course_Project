package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import edu.byu.cs.tweeter.model.service.response.UserFollowResponse;
import edu.byu.cs.tweeter.presenter.FollowPresenter;

public class CheckFollowTask extends AsyncTask<UserFollowRequest, Void, UserFollowResponse> {

    private final FollowPresenter presenter;
    private final CheckFollowTask.Observer observer;
    private Exception exception;

    public interface Observer {
        void followInformation(UserFollowResponse userFollowResponse);
        void handleException(Exception exception);
    }

    public CheckFollowTask(FollowPresenter presenter, CheckFollowTask.Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected UserFollowResponse doInBackground(UserFollowRequest... checkFollowRequests) {

        UserFollowResponse response = null;

        try {
            response = presenter.checkFollow(checkFollowRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(UserFollowResponse userFollowResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.followInformation(userFollowResponse);
        }
    }
}
