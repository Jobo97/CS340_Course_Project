package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.net.ServerFacade_Old;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

public class MainService {
    public Response postStatus(PostStatusRequest request) {
        ServerFacade_Old serverFacade = getServerFacade();
        Response response = serverFacade.postStatus(request);
        return response;
    }

    ServerFacade_Old getServerFacade() {
        return new ServerFacade_Old();
    }
}
