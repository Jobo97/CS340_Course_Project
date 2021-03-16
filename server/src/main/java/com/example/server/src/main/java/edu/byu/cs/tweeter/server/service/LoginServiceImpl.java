package com.example.server.src.main.java.edu.byu.cs.tweeter.server.service;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.LoginDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.ILoginService;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LoginRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.LoginResponse;

import java.io.IOException;

public class LoginServiceImpl implements ILoginService {

    @Override
    public LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException{

        LoginDAO loginDAO = getLoginDAO();
        LoginResponse loginResponse = loginDAO.login(request);

//        if(loginResponse.isSuccess()) {
//            loadImage(loginResponse.getUser());
//        }

        return loginResponse;
    }


    public LoginDAO getLoginDAO(){ return new LoginDAO(); }
}
