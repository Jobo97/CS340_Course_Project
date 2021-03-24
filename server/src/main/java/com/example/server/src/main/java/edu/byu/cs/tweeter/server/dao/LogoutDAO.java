package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

public class LogoutDAO {

    public Response logout(LogoutRequest request) {
        //We will ignore the request since this is dummy data
        if(request.getUserAlias() == null || request.getToken() == null){
            return new Response(false);
        }
        return new Response(true, "Logout worked");
    }
}
