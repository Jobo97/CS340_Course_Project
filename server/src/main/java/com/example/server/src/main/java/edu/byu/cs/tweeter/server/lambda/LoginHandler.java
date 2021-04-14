package com.example.server.src.main.java.edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LoginRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.LoginResponse;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.LoginServiceImpl;

import java.io.IOException;

/**
 * An AWS lambda function that logs a user in and returns the user object and an auth code for
 * a successful login.
 */
public class LoginHandler implements RequestHandler<LoginRequest, LoginResponse> {
    @Override
    public LoginResponse handleRequest(LoginRequest loginRequest, Context context) {

        LoginServiceImpl loginService = new LoginServiceImpl();
        try {
            System.out.println(loginRequest.toString());
            return loginService.login(loginRequest);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
