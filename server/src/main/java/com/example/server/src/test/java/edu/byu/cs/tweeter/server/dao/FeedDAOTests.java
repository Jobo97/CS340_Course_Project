package com.example.server.src.test.java.edu.byu.cs.tweeter.server.dao;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.FeedDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.Status;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusFeedRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FeedDAOTests {

    FeedDAO feedDAO;

    @BeforeEach
    public void setup() {
        feedDAO = new FeedDAO();
    }

    @Test
    public void getFeedPaginated_should_returnStatuses_with_validRequest() {
        String userAlias = "@test";
        int num_statuses = 5;
        StatusResponse response = feedDAO.getFeedPaginated(userAlias, num_statuses, null);
        List<Status> statuses = response.getStatuses();
        Assertions.assertNotNull(statuses);
        Assertions.assertEquals(statuses.size(), num_statuses);

        String tweet1 = "Give da batches plz";
        Assertions.assertTrue(tweet1.equals(statuses.get(0).getTweet()));

        String tweet2 = "We include JSON!";
        Assertions.assertTrue(tweet2.equals(statuses.get(1).getTweet()));

        String tweet3 = "Feed Batch again";
        Assertions.assertTrue(tweet3.equals(statuses.get(2).getTweet()));

        String tweet4 = "Cleared the SQS, time trial for feed";
        Assertions.assertTrue(tweet4.equals(statuses.get(3).getTweet()));

        String tweet5 = "Updated Feed with only Alias' not full user objects";
        Assertions.assertTrue(tweet5.equals(statuses.get(4).getTweet()));
    }

    @Test
    public void getFeedPaginated_should_returnNoStatuses_with_badRequest() {
        StatusResponse response = feedDAO.getFeedPaginated(null, -1, null);
        Assertions.assertEquals(response.getStatuses().size(), 0);
    }

    @Test
    public void putFeed_should_returnTrue_with_validRequest() {
        List<PostStatusFeedRequest> requests = new ArrayList<>();
        Assertions.assertTrue(feedDAO.putFeed(requests));
    }

    @Test
    public void putFeed_should_returnFalse_with_badRequest() {
        Assertions.assertFalse(feedDAO.putFeed(null));
    }
}
