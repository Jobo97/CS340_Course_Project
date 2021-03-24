package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

public class MainDAO {
    public Response postStatus(PostStatusRequest request) {
        //would add the tweet to the data base
        if(request.getAlias() == null || request.getTweet() == null){
            return new Response(false);
        }
        return new Response(true);
    }
}
