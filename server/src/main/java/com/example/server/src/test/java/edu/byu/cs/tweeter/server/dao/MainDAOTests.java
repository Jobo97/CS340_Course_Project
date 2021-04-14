package com.example.server.src.test.java.edu.byu.cs.tweeter.server.dao;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.MainDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainDAOTests {

    MainDAO mainDAO;

    @BeforeEach
    public void setup() {
        mainDAO = new MainDAO();
    }

    @Test
    public void postStatus_returnsFollowers_with_goodRequest() {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        Date date = new Date(System.currentTimeMillis());
        try{
            date = dateFormat.parse(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestamp = new Timestamp(date.getTime());
        String tweet = "test tweet";
        String alias = "@test";
        String timeStampString = timestamp.toString();
        PostStatusRequest request = new PostStatusRequest(tweet, alias, timeStampString);
        List<String> followers = mainDAO.postStatus(request);

        int expectedNumFollowers = 3;
        Assertions.assertEquals(followers.size(), expectedNumFollowers);

        String expectedFollowerAlias1 = "@guy2";
        Assertions.assertTrue(followers.get(0).equals(expectedFollowerAlias1));
        String expectedFollowerAlias2 = "@guy1";
        Assertions.assertTrue(followers.get(1).equals(expectedFollowerAlias2));
        String expectedFollowerAlias3 = "@carter";
        Assertions.assertTrue(followers.get(2).equals(expectedFollowerAlias3));

    }

    @Test
    public void postStatus_returnsNull_with_badRequest() {
        Assertions.assertNull(mainDAO.postStatus(new PostStatusRequest(null, null, null)));
    }

}
