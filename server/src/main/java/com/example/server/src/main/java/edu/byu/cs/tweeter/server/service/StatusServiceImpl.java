package com.example.server.src.main.java.edu.byu.cs.tweeter.server.service;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.StatusDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.IStatusService;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

import java.io.IOException;

public class StatusServiceImpl implements IStatusService {
    @Override
    public StatusResponse getStatuses(StatusRequest request) throws IOException {
        return getStatusDAO().getStatuses(request);
    }

    public StatusDAO getStatusDAO(){ return new StatusDAO(); }
}
