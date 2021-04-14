package com.example.server.src.main.java.edu.byu.cs.tweeter.server.service;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.FollowDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.IFollowingService;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.UserFollowResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowServiceImpl implements IFollowingService {

    @Override
    public FollowResponse getFollows(FollowRequest request) throws IOException {
        if (getAuthTokenDAO().validateSession(request.getFollowerAlias())) {
            FollowResponse response = getFollowingDAO().getFollows(request);
            return response;
        }
        else {
            FollowResponse fr = new FollowResponse();
            fr.setMessage("User-session timeout");
            List<User> list = new ArrayList<>();
            fr.setFollows(list);
            return fr;
        }

//        if(response.isSuccess()) {
//            loadImages(response);
//        }

    }

    @Override
    public UserFollowResponse checkFollow(UserFollowRequest request) throws IOException {
        if (getAuthTokenDAO().validateSession(request.getUserAlias())) {
            UserFollowResponse response = getFollowingDAO().checkFollows(request);
            return response;
        }
        else {
            return new UserFollowResponse(false, false);
        }

    }

    @Override
    public FollowCountResponse getFollowCount(FollowCountRequest request) throws IOException {
        if (getAuthTokenDAO().validateSession(request.getUserAlias())) {
            FollowCountResponse response = getFollowingDAO().getFollowCount(request);
            return response;
        }
        else {
            return new FollowCountResponse(false, -1, -1);
        }
    }

    @Override
    public UserFollowResponse followStatus(UserFollowRequest request) throws IOException{
        if (getAuthTokenDAO().validateSession(request.getUserAlias())) {
            UserFollowResponse response = getFollowingDAO().followStatus(request);
            return response;
        }
        else {
            return new UserFollowResponse(false, false);
        }
    }

    public FollowDAO getFollowingDAO() {
        return new FollowDAO();
    }

    public AuthTokenDAO getAuthTokenDAO(){ return new AuthTokenDAO(); }

}
