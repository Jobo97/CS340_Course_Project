package edu.byu.cs.tweeter.model.net;

import java.io.IOException;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LoginRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.LoginResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.UserFollowResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    // TODO: Set this to the invoke URL of your API. Find it by going to your API in AWS, clicking
    //  on stages in the right-side menu, and clicking on the stage you deployed your API to.
    private static final String SERVER_URL = "https://dqpbjev2bk.execute-api.us-west-2.amazonaws.com/dev";

    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    /**
     * Performs a login and if successful, returns the logged in user and an auth token.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request, String urlPath) throws IOException, TweeterRemoteException {
        LoginResponse response = clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public FollowResponse getFollows(FollowRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        FollowResponse response = clientCommunicator.doPost(urlPath, request, null, FollowResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public StatusResponse getStatuses(StatusRequest request, String urlPath) throws IOException, TweeterRemoteException {
        StatusResponse response = clientCommunicator.doPost(urlPath, request, null, StatusResponse.class);
                                                    //Do post?
        if(response.isSuccess()){
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public Response postStatus(PostStatusRequest request, String urlPath) throws IOException, TweeterRemoteException {
        Response response = clientCommunicator.doPost(urlPath, request, null, Response.class);
                                                //Do post?
        if(response.isSuccess()){
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public UserFollowResponse checkFollows(UserFollowRequest request, String urlPath) throws IOException, TweeterRemoteException {
        UserFollowResponse response = clientCommunicator.doPost(urlPath, request, null, UserFollowResponse.class);
                                                        //Do post?
        if(response.isSuccess()){
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public FollowCountResponse getFollowCount(FollowCountRequest request, String urlPath) throws IOException, TweeterRemoteException {
        FollowCountResponse response = clientCommunicator.doPost(urlPath, request, null, FollowCountResponse.class);
                                                        //Do post?
        if(response.isSuccess()){
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public UserFollowResponse followStatus(UserFollowRequest request, String urlPath) throws IOException, TweeterRemoteException {
        UserFollowResponse response = clientCommunicator.doPost(urlPath, request, null, UserFollowResponse.class);
                                                        //Do post?
        if(response.isSuccess()){
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public GetUserResponse getUser(GetUserRequest request, String urlPath) throws IOException, TweeterRemoteException {
        GetUserResponse response = clientCommunicator.doPost(urlPath, request, null, GetUserResponse.class);
                                                    //Do post?
        if(response.isSuccess()){
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public Response logout(LogoutRequest request, String urlPath) throws IOException, TweeterRemoteException {
        Response response = clientCommunicator.doPost(urlPath, request, null, Response.class);
                                                //Do post?
        if(response.isSuccess()){
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }
}