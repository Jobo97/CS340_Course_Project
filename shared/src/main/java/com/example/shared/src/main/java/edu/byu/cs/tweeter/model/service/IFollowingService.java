package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.UserFollowResponse;

/**
 * Defines the interface for the 'following' service.
 */
public interface IFollowingService {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    FollowResponse getFollows(FollowRequest request)
            throws IOException, TweeterRemoteException;

    UserFollowResponse checkFollow(UserFollowRequest request) throws IOException, TweeterRemoteException;
    FollowCountResponse getFollowCount(FollowCountRequest request) throws IOException, TweeterRemoteException;

    UserFollowResponse followStatus(UserFollowRequest request) throws IOException, TweeterRemoteException;
}
