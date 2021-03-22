package com.example.server.src.main.java.edu.byu.cs.tweeter.server.service;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.LogoutDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.ILogoutService;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

import java.io.IOException;

public class LogoutServiceImpl implements ILogoutService {
    @Override
    public Response logout(LogoutRequest request) throws IOException {
        return getLogoutDAO().logout(request);
    }

    public LogoutDAO getLogoutDAO(){ return new LogoutDAO(); }
}
