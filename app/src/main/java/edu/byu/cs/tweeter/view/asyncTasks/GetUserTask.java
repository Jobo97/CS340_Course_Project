package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import edu.byu.cs.tweeter.presenter.ProfilePresenter;

public class GetUserTask extends AsyncTask<GetUserRequest, Void, GetUserResponse> {

    private final ProfilePresenter presenter;
    private final GetUserTask.Observer observer;
    private Exception exception;

    public interface Observer {
        void loadUser(GetUserResponse getUserResponse);
        void handleException(Exception exception);
    }

    public GetUserTask(ProfilePresenter presenter, GetUserTask.Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected GetUserResponse doInBackground(GetUserRequest... userRequests) {

        GetUserResponse response = null;

        try {
            response = presenter.getUser(userRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(GetUserResponse getUserResponse) {
        if (exception != null) {
            observer.handleException(exception);
        } else {
            observer.loadUser(getUserResponse);
        }
    }
}

