package com.example.server.src.main.java.edu.byu.cs.tweeter.server.service;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.FollowDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.IFollowingService;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.UserFollowResponse;

import java.io.IOException;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowServiceImpl implements IFollowingService {

    @Override
    public FollowResponse getFollows(FollowRequest request) throws IOException {
        FollowResponse response = getFollowingDAO().getFollows(request);

//        if(response.isSuccess()) {
//            loadImages(response);
//        }

        return response;
    }

    @Override
    public UserFollowResponse checkFollow(UserFollowRequest request) throws IOException {
        UserFollowResponse response = getFollowingDAO().checkFollows(request);
        return response;
    }

    @Override
    public FollowCountResponse getFollowCount(FollowCountRequest request) throws IOException {
        FollowCountResponse response = getFollowingDAO().getFollowCount(request);
        return response;
    }

    @Override
    public UserFollowResponse followStatus(UserFollowRequest request) throws IOException{
        UserFollowResponse response = getFollowingDAO().followStatus(request);
        return response;
    }

    public FollowDAO getFollowingDAO() {
        return new FollowDAO();
    }
}
