package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.ServerFacade_Old;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.IFollowingService;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.UserFollowResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowServiceProxy implements IFollowingService {

    static final String URL_PATH_GET_FOLLOWS = "/getfollow";
    static final String URL_PATH_CHECK_FOLLOW = "/checkfollow";
    static final String URL_PATH_FOLLOW_COUNT = "/getfollowcount";
    static final String URL_PATH_FOLLOW_STATUS = "/followstatus";

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
     public FollowResponse getFollows(FollowRequest request) throws IOException, TweeterRemoteException {
        FollowResponse response = getServerFacade().getFollows(request, URL_PATH_GET_FOLLOWS);

        if(response.isSuccess()) {
            loadImages(response);
        }

        return response;
     }
    @Override
    public UserFollowResponse checkFollow(UserFollowRequest request) throws IOException, TweeterRemoteException {
        UserFollowResponse response = getServerFacade().checkFollows(request, URL_PATH_CHECK_FOLLOW);
        return response;
    }
    @Override
    public FollowCountResponse getFollowCount(FollowCountRequest request) throws IOException, TweeterRemoteException {
        FollowCountResponse response = getServerFacade().getFollowCount(request, URL_PATH_FOLLOW_COUNT);
        return response;
    }
    @Override
    public UserFollowResponse followStatus(UserFollowRequest request) throws IOException, TweeterRemoteException{
        UserFollowResponse response = getServerFacade().followStatus(request, URL_PATH_FOLLOW_STATUS);
        return response;
    }
    public void loadImages(FollowResponse response) throws IOException {
        for(User user : response.getFollows()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }
//    @Override
//    public FollowingResponse getFollowees(FollowingRequest request) throws IOException, TweeterRemoteException {
//        FollowingResponse response = getServerFacade().getFollowees(request, URL_PATH);
//
//        if(response.isSuccess()) {
//            loadImages(response);
//        }
//
//        return response;
//    }

    /**
     * Loads the profile image data for each followee included in the response.
     *
     * @param response the response from the followee request.
     */
//    private void loadImages(FollowingResponse response) throws IOException {
//        for(User user : response.getFollowees()) {
//            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
//            user.setImageBytes(bytes);
//        }
//    }

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
