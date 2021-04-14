package com.example.server.src.test.java.edu.byu.cs.tweeter.server.dao;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.LoginDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.LogoutDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LoginRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.LoginResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sun.rmi.runtime.Log;

public class AuthTokenDAOTests {

    AuthTokenDAO authTokenDAO;
    LoginDAO loginDAO;
    LogoutDAO logoutDAO;
    String alias;
    AuthToken authToken;


    @BeforeEach
    public void setup() {
        alias = "@newtest";
        String password = "password";
        authTokenDAO = new AuthTokenDAO();
        loginDAO = new LoginDAO();
        LoginResponse response = loginDAO.login(new LoginRequest(alias, password));
        authToken = response.getAuthToken();
    }

    @AfterEach
    public void logout() {
        logoutDAO = new LogoutDAO();
        logoutDAO.logout(new LogoutRequest(alias, authToken));
    }

    @Test
    public void validateSession_returnsFalse_with_loggedInAlias() {
        Assertions.assertFalse(authTokenDAO.validateSession(alias));
    }

    @Test
    public void validateSession_returnsFalse_with_notLoggedInAlias() {
        Assertions.assertFalse(authTokenDAO.validateSession(null));
    }

    @Test
    public void get_returnsExpectedToken_with_loggedInAlias() {
        Assertions.assertNotNull(authTokenDAO.get(alias));
    }

    @Test
    public void get_returnsNull_with_notLoggedInAlias() {
        Assertions.assertNull(authTokenDAO.get(null));
    }

    @Test
    public void put_returnsTrue_with_notLoggedInAlias() {
        Assertions.assertTrue(authTokenDAO.put("@carter", "test token"));
        authTokenDAO.delete("@carter");
    }

    @Test
    public void put_returnsTrue_with_loggedInAlias() {
        Assertions.assertTrue(authTokenDAO.delete(alias));
    }

    @Test
    public void update_returnsTrue_with_loggedInAlias() {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        Date date = new Date(System.currentTimeMillis());
        try{
            date = dateFormat.parse(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestamp = new Timestamp(date.getTime());
        Assertions.assertTrue(authTokenDAO.update(alias, timestamp.toString()));
    }

    @Test
    public void update_returnsFalse_with_notLoggedInAlias() {
        Assertions.assertFalse(authTokenDAO.update(null, null));
    }

    @Test
    public void delete_returnsTrue_with_loggedInAlias() {
        Assertions.assertTrue(authTokenDAO.delete(alias));
    }

    @Test
    public void delete_returnsFalse_with_notLoggedInAlias() {
        Assertions.assertFalse(authTokenDAO.delete(null));
    }
}
