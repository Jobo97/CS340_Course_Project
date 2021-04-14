package com.example.server.src.test.java.edu.byu.cs.tweeter.server.dao;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.StoryDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.Status;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StoryDAOTests {

    StoryDAO storyDAO;

    @BeforeEach
    public void setup() {
        storyDAO = new StoryDAO();
    }

    @Test
    public void getStoryPaginated_returns_goodResponse_with_goodRequest() {
        String alias = "@carter";
        int numStatuses = 5;
        StatusResponse response = storyDAO.getStoryPaginated(alias, numStatuses, null);
        List<Status> statuses = response.getStatuses();
        Assertions.assertTrue(statuses.size() <= numStatuses);
    }

    @Test
    public void getStoryPaginated_returns_badResponse_with_badRequest() {
        StatusResponse response = storyDAO.getStoryPaginated(null, -1, null);
        Assertions.assertEquals(response.getStatuses().size(), 0);
    }

    @Test
    public void putStory_returns_true_with_goodRequest() {
        String alias = "@test";
        String tweet = "test tweet";
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        Date date = new Date(System.currentTimeMillis());
        try{
            date = dateFormat.parse(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestamp = new Timestamp(date.getTime());
        String timeStampString = timestamp.toString();
        Assertions.assertTrue(storyDAO.putStory(alias, tweet, timeStampString));
    }

    @Test
    public void putStory_returns_false_with_nullAlias() {
        Assertions.assertFalse(storyDAO.putStory(null, null, null));
    }

}
