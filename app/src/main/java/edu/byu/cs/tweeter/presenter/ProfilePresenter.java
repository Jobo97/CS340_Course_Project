package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.ProfileService;
import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;

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

    public GetUserResponse getUser(GetUserRequest request) throws IOException {
        ProfileService profileService = getProfileService();
        return profileService.getUser(request);
    }

    ProfileService getProfileService() {
        return new ProfileService();
    }
}
