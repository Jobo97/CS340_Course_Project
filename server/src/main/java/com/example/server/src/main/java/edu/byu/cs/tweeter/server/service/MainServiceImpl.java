package com.example.server.src.main.java.edu.byu.cs.tweeter.server.service;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.MainDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.IMainService;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusFeedRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

import java.util.List;

public class MainServiceImpl implements IMainService {
    @Override
    public List<String> postStatus(PostStatusRequest request) {
        return getMainDAO().postStatus(request);
    }

    public MainDAO getMainDAO(){ return new MainDAO(); }

}
