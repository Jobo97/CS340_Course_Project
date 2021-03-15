package com.example.server.src.main.java.edu.byu.cs.tweeter.server.service;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.MainDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.IMainService;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

public class MainServiceImpl implements IMainService {
    @Override
    public Response postStatus(PostStatusRequest request) {
        return getMainDAO().postStatus(request);
    }

    MainDAO getMainDAO(){ return new MainDAO(); }
}
