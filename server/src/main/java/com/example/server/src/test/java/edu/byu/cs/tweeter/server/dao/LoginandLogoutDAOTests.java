package com.example.server.src.test.java.edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.LoginDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.LogoutDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LoginRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.LoginResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import javax.swing.JPasswordField;

public class LoginandLogoutDAOTests {

    LoginDAO loginDAO;
    LogoutDAO logoutDAO;

    @BeforeEach
    public void setup() {
        loginDAO = new LoginDAO();
        logoutDAO = new LogoutDAO();
    }

    @Test
    public void registerAndLogout_return_goodResponses_with_goodRequests() {
        String username = "@newtest";
        String password = "password";
        String firstname = "testy";
        String lastname = "mctest";
        String imageURL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        byte[] imageBytes = new byte[0];
        try {
            imageBytes = ByteArrayUtils.bytesFromUrl(imageURL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoginRequest loginRequest = new LoginRequest(username, password, firstname, lastname, imageBytes);
        LoginResponse loginResponse = loginDAO.login(loginRequest);
        Assertions.assertTrue(loginResponse.isSuccess());
        Assertions.assertTrue(username.equals(loginResponse.getUser().getAlias()));

        LogoutRequest logoutRequest = new LogoutRequest(loginResponse.getUser().getAlias(), loginResponse.getAuthToken());
        Response logoutResponse = logoutDAO.logout(logoutRequest);
        Assertions.assertTrue(logoutResponse.isSuccess());
    }

    @Test
    public void loginAndLogout_return_goodResponse_with_goodRequest() {
        String username = "@newtest";
        String password = "password";
        LoginRequest loginRequest = new LoginRequest(username, password);
        LoginResponse loginResponse = loginDAO.login(loginRequest);
        Assertions.assertTrue(loginResponse.isSuccess());
        Assertions.assertTrue(username.equals(loginResponse.getUser().getAlias()));

        LogoutRequest logoutRequest = new LogoutRequest(loginResponse.getUser().getAlias(), loginResponse.getAuthToken());
        Response logoutResponse = logoutDAO.logout(logoutRequest);
        Assertions.assertTrue(logoutResponse.isSuccess());
    }

    @Test
    public void login_throws_with_badRequest() {
        Assertions.assertThrows(AmazonDynamoDBException.class, () -> loginDAO.login(new LoginRequest(null, null)));
    }

    @Test
    public void logout_returns_badResponse_with_badRequest() {
        Response response = logoutDAO.logout(new LogoutRequest(null, null));
        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    public void register_returns_badResponse_with_badRequest() {
        Assertions.assertThrows(NullPointerException.class, () -> loginDAO.login(new LoginRequest(null, null, null, null, null)));
    }
}
