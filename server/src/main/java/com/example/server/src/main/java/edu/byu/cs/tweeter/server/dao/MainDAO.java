package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

public class MainDAO {
    public Response postStatus(PostStatusRequest request) {
        //would add the tweet to the data base
        return new Response(true);
    }
}
