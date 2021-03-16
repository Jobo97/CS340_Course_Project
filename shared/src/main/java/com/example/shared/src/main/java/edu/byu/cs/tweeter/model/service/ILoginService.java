package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LoginRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.LoginResponse;

public interface ILoginService {

    LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException;

}
