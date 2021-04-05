package edu.byu.cs.tweeter.model.service;

import java.io.IOException;
import java.util.Base64;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.ILoginService;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LoginRequest;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

/**
 * Contains the business logic to support the login operation.
 */
public class LoginServiceProxy implements ILoginService {

    private static final String URL_PATH = "/login";

    public LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException, RuntimeException {
        ServerFacade serverFacade = getServerFacade();
        //LoginResponse loginResponse = serverFacade.login(request, URL_PATH);

        if (request.getImageBytes() != null)
        {
            String base64Image = Base64.getEncoder().encodeToString(request.getImageBytes());
            request.setImageEncoded(base64Image);
        }

        LoginResponse loginResponse = serverFacade.login(request, URL_PATH);

        if(loginResponse.isSuccess() && loginResponse.getUser() != null) {
            loadImage(loginResponse.getUser());
        }

        return loginResponse;
    }

    /**
     * Loads the profile image data for the user.
     *
     * @param user the user whose profile image data is to be loaded.
     */
    private void loadImage(User user) throws IOException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
        user.setImageBytes(bytes);
    }

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
