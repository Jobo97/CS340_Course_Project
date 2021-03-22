package com.example.server.src.test.java.edu.byu.cs.tweeter.server.service;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.MainDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.MainServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class MainServiceImplTest {

    private PostStatusRequest postStatusRequest;
    private Response response;
    private MainDAO mainDAO;
    private MainServiceImpl mainService;
    private PostStatusRequest invalidRequest;
    private Response invalidResponse;

    @BeforeEach
    public void setup() {
        postStatusRequest = new PostStatusRequest("tweet", "alias");
        response = new Response(true);
        invalidRequest = new PostStatusRequest(null, null);
        invalidResponse = new Response(false);
        mainDAO = Mockito.mock(MainDAO.class);
        mainService = Mockito.spy(MainServiceImpl.class);
        Mockito.when(mainService.getMainDAO()).thenReturn(mainDAO);
    }

    @Test
    public void testPostStatus_validRequest_correctResponse() {
        Mockito.when(mainDAO.postStatus(postStatusRequest)).thenReturn(response);
        Response actualResponse = mainService.postStatus(postStatusRequest);
        Assertions.assertEquals(actualResponse, response);
    }

    @Test
    public void testPostStatus_invalidRequest_incorrectResponse() {
        Mockito.when(mainDAO.postStatus(invalidRequest)).thenReturn(invalidResponse);
        Response actualResponse = mainService.postStatus(invalidRequest);
        Assertions.assertEquals(actualResponse, invalidResponse);
    }

}
