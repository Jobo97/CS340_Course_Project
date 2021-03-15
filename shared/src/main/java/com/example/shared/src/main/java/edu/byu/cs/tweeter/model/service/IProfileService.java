package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;

import java.io.IOException;

public interface IProfileService {

    GetUserResponse getUser(GetUserRequest request) throws IOException;

}
