package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

import java.io.IOException;

public interface ILogoutService {
    Response logout(LogoutRequest request) throws IOException;
}
