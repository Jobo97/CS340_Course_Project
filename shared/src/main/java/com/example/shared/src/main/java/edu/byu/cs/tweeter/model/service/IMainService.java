package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

public interface IMainService {
    Response postStatus(PostStatusRequest request);
}
