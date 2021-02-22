package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import edu.byu.cs.tweeter.model.service.response.UserFollowResponse;
import edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

/**
 * The presenter for the "following" functionality of the application.
 */
public class FollowPresenter {

    private final View view;



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
    public FollowPresenter(View view) {
        this.view = view;
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public FollowResponse getFollows(FollowRequest request) throws IOException {
        FollowService followService = getFollowService();
        return followService.getFollows(request);
    }

    public FollowCountResponse getFollowCount(FollowCountRequest request) throws IOException {
        FollowService followService = getFollowService();
        return followService.getFollowCount(request);
    }

    public UserFollowResponse checkFollow(UserFollowRequest request) throws IOException {
        FollowService followService = getFollowService();
        return followService.checkFollow(request);
    }

    public UserFollowResponse followStatus(UserFollowRequest userFollowRequest) throws IOException {
        FollowService followService = getFollowService();
        return followService.followStatus(userFollowRequest);
    }

    /**
     * Returns an instance of {@link FollowService}. Allows mocking of the FollowingService class
     * for testing purposes. All usages of FollowingService should get their FollowingService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FollowService getFollowService() {
        return new FollowService();
    }
}
