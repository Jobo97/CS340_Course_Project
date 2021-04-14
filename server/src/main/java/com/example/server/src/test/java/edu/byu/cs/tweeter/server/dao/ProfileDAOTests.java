package com.example.server.src.test.java.edu.byu.cs.tweeter.server.dao;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.ProfileDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProfileDAOTests {

    ProfileDAO profileDAO;

    @BeforeEach
    public void setup() {
        profileDAO = new ProfileDAO();
    }

    @Test
    public void getUser_returns_goodResponse_with_goodRequest() {
        String alias = "@test";
        GetUserRequest request = new GetUserRequest(alias);
        GetUserResponse response = profileDAO.getUser(request);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertTrue(response.getViewedUser().getAlias().equals(alias));
    }

    @Test
    public void getUser_returns_failedResponse_with_badRequest() {
        GetUserResponse response = profileDAO.getUser(new GetUserRequest(null));
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getViewedUser());
    }
}
