package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.ServerFacade_Old;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.IFollowingService;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.UserFollowResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowService implements IFollowingService {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link ServerFacade_Old} to
     * get the followees from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    @Override
    public FollowResponse getFollows(FollowRequest request) throws IOException {
        FollowResponse response = getServerFacade().getFollows(request);

        if(response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }

    @Override
    public UserFollowResponse checkFollow(UserFollowRequest request) throws IOException {
        UserFollowResponse response = getServerFacade().checkFollows(request);
        return response;
    }

    @Override
    public FollowCountResponse getFollowCount(FollowCountRequest request) throws IOException {
        FollowCountResponse response = getServerFacade().getFollowCount(request);
        return response;
    }

    @Override
    public UserFollowResponse followStatus(UserFollowRequest request) throws IOException{
        UserFollowResponse response = getServerFacade().followStatus(request);
        return response;
    }

    /**
     * Loads the profile image data for each followee included in the response.
     *
     * @param response the response from the followee request.
     */
    public void loadImages(FollowResponse response) throws IOException {
        for(User user : response.getFollows()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }

    /**
     * Returns an instance of {@link ServerFacade_Old}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    ServerFacade_Old getServerFacade() {
        return new ServerFacade_Old();
    }


}
