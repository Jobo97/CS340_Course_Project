package com.example.server.src.main.java.edu.byu.cs.tweeter.server.service;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.LogoutDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.ProfileDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.IProfileService;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;

import java.io.IOException;

public class ProfileServiceImpl implements IProfileService {
    @Override
    public GetUserResponse getUser(GetUserRequest request) throws IOException {
        return getProfileDAO().getUser(request);
    }

    public ProfileDAO getProfileDAO(){ return new ProfileDAO(); }
}
