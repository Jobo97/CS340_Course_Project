package com.example.server.src.test.java.edu.byu.cs.tweeter.server.dao;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.UserDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDAOTests {

    UserDAO userDAO;

    @BeforeEach
    public void setup() {
        userDAO = new UserDAO();
    }

    @Test
    public void get_returns_userAssociated_with_givenAlias() {
        String alias = "@test";
        User user = userDAO.get(alias);
        Assertions.assertTrue(user.getAlias().equals(alias));
    }

    @Test
    public void get_returns_Null_with_badAlias() {
        Assertions.assertNull(userDAO.get(null));
    }

}
