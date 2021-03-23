package com.example.server.src.test.java.edu.byu.cs.tweeter.server.service;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.LoginDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.LoginServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LoginRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.LoginResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class LoginServiceImplTest {

    LoginRequest loginRequest;
    LoginResponse loginResponse;

    LoginServiceImpl loginService;

    LoginDAO loginDAO;
    private LoginRequest invalidRequest;
    private LoginResponse invalidResponse;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);
        AuthToken authToken = new AuthToken("authToken");

        loginRequest = new LoginRequest("username", "password");
        loginResponse = new LoginResponse(currentUser, authToken);
        invalidRequest = new LoginRequest(null, null);
        invalidResponse = new LoginResponse(null, null);

        loginDAO = Mockito.mock(LoginDAO.class);

        loginService = Mockito.spy(LoginServiceImpl.class);
        Mockito.when(loginService.getLoginDAO()).thenReturn(loginDAO);
    }

    @Test
    public void testLogin_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        Mockito.when(loginDAO.login(loginRequest)).thenReturn(loginResponse);
        LoginResponse response = loginService.login(loginRequest);
        Assertions.assertEquals(response, loginResponse);
    }

    @Test
    public void testLogin_invalidRequest_incorrectResponse() throws IOException, TweeterRemoteException {
        Mockito.when(loginDAO.login(invalidRequest)).thenReturn(invalidResponse);
        LoginResponse response = loginService.login(invalidRequest);
        Assertions.assertEquals(response, invalidResponse);
    }

}
