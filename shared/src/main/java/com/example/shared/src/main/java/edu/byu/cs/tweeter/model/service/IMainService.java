package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

import java.util.List;

public interface IMainService {
    List<String> postStatus(PostStatusRequest request);
}
