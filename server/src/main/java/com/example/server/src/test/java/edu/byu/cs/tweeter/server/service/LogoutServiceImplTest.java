package com.example.server.src.test.java.edu.byu.cs.tweeter.server.service;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.LogoutDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.LogoutServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;


public class LogoutServiceImplTest {

    LogoutRequest logoutRequest;
    Response response;
    LogoutDAO logoutDAO;
    LogoutServiceImpl logoutService;
    private LogoutRequest invalidRequest;
    private Response invalidResponse;

    @BeforeEach
    public void setup() {
        logoutRequest = new LogoutRequest("alias", new AuthToken("authToken"));
        response = new Response(true);
        invalidRequest = new LogoutRequest(null, null);
        invalidResponse = new Response(false);
        logoutDAO = Mockito.mock(LogoutDAO.class);
        logoutService = Mockito.spy(LogoutServiceImpl.class);
        Mockito.when(logoutService.getLogoutDAO()).thenReturn(logoutDAO);
    }

    @Test
    public void testLogout_validRequest_correctResponse() throws IOException {
        Mockito.when(logoutDAO.logout(logoutRequest)).thenReturn(response);
        Response actualResponse = logoutService.logout(logoutRequest);
        Assertions.assertEquals(actualResponse, response);
    }

    @Test
    public void testLogout_invalidRequest_incorrectResponse() throws IOException {
        Mockito.when(logoutDAO.logout(invalidRequest)).thenReturn(invalidResponse);
        Response actualResponse = logoutService.logout(invalidRequest);
        Assertions.assertEquals(actualResponse, invalidResponse);
    }

}
