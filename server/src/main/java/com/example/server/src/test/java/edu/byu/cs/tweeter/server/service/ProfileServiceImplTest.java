package com.example.server.src.test.java.edu.byu.cs.tweeter.server.service;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.ProfileDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.ProfileServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class ProfileServiceImplTest {

    private GetUserRequest getUserRequest;
    private GetUserResponse getUserResponse;
    private ProfileDAO profileDAO;
    private ProfileServiceImpl profileService;
    private GetUserRequest invalidUserRequest;
    private GetUserResponse invalidUserResponse;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);
        getUserRequest = new GetUserRequest("alias");
        getUserResponse = new GetUserResponse(true, currentUser);
        invalidUserRequest = new GetUserRequest(null);
        invalidUserResponse = new GetUserResponse(false, null);
        profileDAO = Mockito.mock(ProfileDAO.class);
        profileService = Mockito.spy(ProfileServiceImpl.class);
        Mockito.when(profileService.getProfileDAO()).thenReturn(profileDAO);
    }

    @Test
    public void testGetUser_validRequest_correctResponse() throws IOException {
        Mockito.when(profileDAO.getUser(getUserRequest)).thenReturn(getUserResponse);
        GetUserResponse response = profileService.getUser(getUserRequest);
        Assertions.assertEquals(response, getUserResponse);
    }

    @Test
    public void testGetUser_invalidRequest_incorrectResponse() throws IOException {
        Mockito.when(profileDAO.getUser(invalidUserRequest)).thenReturn(invalidUserResponse);
        GetUserResponse response = profileService.getUser(invalidUserRequest);
        Assertions.assertEquals(response, invalidUserResponse);
    }

}
