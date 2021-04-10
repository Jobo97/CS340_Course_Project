package com.example.server.src.test.java.edu.byu.cs.tweeter.server.service;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.FeedDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.StoryDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.StatusServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class StatusServiceImplTest {

    private StatusRequest statusRequest;
    private StatusResponse statusResponse;
    private StoryDAO storyDAO;
    private FeedDAO feedDAO;
    private StatusServiceImpl statusService;
    private StatusRequest invalidRequest;
    private StatusResponse invalidResponse;

    @BeforeEach
    public void setup() {
        statusRequest = new StatusRequest("alias", 10, "status", true);
        statusResponse = new StatusResponse("message");
        invalidRequest = new StatusRequest(null, -1, null, false);
        invalidResponse = new StatusResponse("failed message");
        storyDAO = Mockito.mock(StoryDAO.class);
        feedDAO = Mockito.mock(FeedDAO.class);
        statusService = Mockito.spy(StatusServiceImpl.class);
        Mockito.when(statusService.getStoryDAO()).thenReturn(storyDAO);
        Mockito.when(statusService.getFeedDAO()).thenReturn(feedDAO);
    }

    @Test
    public void testGetStatuses_validRequest_correctResponse() throws IOException {
        Mockito.when(storyDAO.getStoryPaginated(statusRequest.getUserAlias(), statusRequest.getLimit())).thenReturn(statusResponse);
        StatusResponse response = statusService.getStatuses(statusRequest);
        Assertions.assertEquals(response, statusResponse);
    }

    @Test
    public void testGetStatuses_invalidRequest_incorrectResponse() throws IOException {
        Mockito.when(storyDAO.getStoryPaginated(invalidRequest.getUserAlias(), invalidRequest.getLimit())).thenReturn(invalidResponse);
        StatusResponse response = statusService.getStatuses(invalidRequest);
        Assertions.assertEquals(response, invalidResponse);
    }
}
