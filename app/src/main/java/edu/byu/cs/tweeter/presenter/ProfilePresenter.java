package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.ProfileService;
import edu.byu.cs.tweeter.model.service.ProfileServiceProxy;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;

public class ProfilePresenter {
    private final ProfilePresenter.View view;



    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public ProfilePresenter(ProfilePresenter.View view) {
        this.view = view;
    }

    public GetUserResponse getUser(GetUserRequest request) throws IOException, TweeterRemoteException, RuntimeException {
        ProfileServiceProxy profileServiceProxy = getProfileServiceProxy();
        return profileServiceProxy.getUser(request);
    }

    ProfileServiceProxy getProfileServiceProxy() {
        return new ProfileServiceProxy();
    }
}
