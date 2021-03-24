package com.example.server.src.test.java.edu.byu.cs.tweeter.server.service;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.StatusDAO;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.service.StatusServiceImpl;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.Status;
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
    private StatusDAO statusDAO;
    private StatusServiceImpl statusService;
    private StatusRequest invalidRequest;
    private StatusResponse invalidResponse;

    @BeforeEach
    public void setup() {
        statusRequest = new StatusRequest("alias", 10, "status", true);
        statusResponse = new StatusResponse("message");
        invalidRequest = new StatusRequest(null, -1, null, false);
        invalidResponse = new StatusResponse("failed message");
        statusDAO = Mockito.mock(StatusDAO.class);
        statusService = Mockito.spy(StatusServiceImpl.class);
        Mockito.when(statusService.getStatusDAO()).thenReturn(statusDAO);
    }

    @Test
    public void testGetStatuses_validRequest_correctResponse() throws IOException {
        Mockito.when(statusDAO.getStatuses(statusRequest)).thenReturn(statusResponse);
        StatusResponse response = statusService.getStatuses(statusRequest);
        Assertions.assertEquals(response, statusResponse);
    }

    @Test
    public void testGetStatuses_invalidRequest_incorrectResponse() throws IOException {
        Mockito.when(statusDAO.getStatuses(invalidRequest)).thenReturn(invalidResponse);
        StatusResponse response = statusService.getStatuses(invalidRequest);
        Assertions.assertEquals(response, invalidResponse);
    }
}
