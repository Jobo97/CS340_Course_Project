package com.example.server.src.test.java.edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.FollowDAO;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.UserFollowResponse;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FollowDAOTests {

    FollowDAO followDAO;

    @BeforeEach
    public void setup() {
        followDAO = new FollowDAO();
    }

    @Test
    public void getFolloweeCount_should_returnCorrectNumber_with_validAlias() {
        int expectedNumFollowees = 2;
        String alias = "@test";
        Assertions.assertEquals(followDAO.getFolloweeCount(alias), expectedNumFollowees);
    }

    @Test
    public void getFolloweeCount_should_throw_with_invalidAlias() {
        Assertions.assertThrows(AmazonDynamoDBException.class, () -> followDAO.getFolloweeCount(null));
    }

    @Test
    public void getFollowerCount_should_returnCorrectNumber_with_validAlias() {
        int expectedNumFollowees = 3;
        String alias = "@test";
        Assertions.assertEquals(followDAO.getFollowerCount(alias), expectedNumFollowees);
    }

    @Test
    public void getFollowerCount_should_throw_with_invalidAlias() {
        Assertions.assertThrows(AmazonDynamoDBException.class, () -> followDAO.getFollowerCount(null));
    }

    @Test
    public void getFollowers_should_returnCorrectFollowers_with_validAlias() {
        String alias = "@test";
        List<String> followers = followDAO.getFollowers(alias);

        String follower1 = "@guy2";
        Assertions.assertTrue(follower1.equals(followers.get(0)));

        String follower2 = "@guy1";
        Assertions.assertTrue(follower2.equals(followers.get(1)));

        String follower3 = "@carter";
        Assertions.assertTrue(follower3.equals(followers.get(2)));

    }

    @Test
    public void getFollowers_should_returnNull_with_invalidAlias() {
        List<String> followers = followDAO.getFollowers(null);
        Assertions.assertNull(followers);
    }

    @Test
    public void getFollowersPaginated_should_returnCorrectFollowers_with_validRequest() {
        String alias = "@test";
        Integer numFollowers = 3;
        FollowResponse response = followDAO.getFollowersPaginated(alias, numFollowers, null);
        List<User> followers = response.getFollows();
        Assertions.assertEquals(numFollowers, followers.size());

        String expectedAlias1 = "@guy2";
        Assertions.assertTrue(followers.get(0).getAlias().equals(expectedAlias1));
        String expectedAlias2 = "@guy1";
        Assertions.assertTrue(followers.get(1).getAlias().equals(expectedAlias2));
        String expectedAlias3 = "@carter";
        Assertions.assertTrue(followers.get(2).getAlias().equals(expectedAlias3));
    }

    @Test
    public void getFollowersPaginated_should_returnNoFollowers_with_invalidRequest() {
        FollowResponse response = followDAO.getFollowersPaginated(null, -1, null);
        Assertions.assertEquals(response.getFollows().size(), 0);
    }

    @Test
    public void getFolloweesPaginated_should_returnCorrectFollowers_with_validRequest() {
        String alias = "@test";
        Integer numFollowers = 2;
        FollowResponse response = followDAO.getFolloweesPaginated(alias, numFollowers, null);
        List<User> followers = response.getFollows();
        Assertions.assertEquals(numFollowers, followers.size());

        String expectedAlias1 = "@carter";
        Assertions.assertTrue(followers.get(0).getAlias().equals(expectedAlias1));
        String expectedAlias2 = "@guy1";
        Assertions.assertTrue(followers.get(1).getAlias().equals(expectedAlias2));
    }

    @Test
    public void getFolloweesPaginated_should_returnNoFollowers_with_invalidRequest() {
        FollowResponse response = followDAO.getFolloweesPaginated(null, -1, null);
        Assertions.assertEquals(response.getFollows().size(), 0);
    }

    @Test
    public void getFollows_should_returnCorrectFollows_with_validRequest() {
        String alias = "@test";
        int limit = 5;
        FollowRequest request = new FollowRequest(alias, limit, null, true);
        FollowResponse response = followDAO.getFollows(request);
        Assertions.assertNotNull(response);
        List<User> follows = response.getFollows();
        Assertions.assertTrue(follows.size() <= limit);

        String expectedAlias1 = "@guy2";
        Assertions.assertTrue(follows.get(0).getAlias().equals(expectedAlias1));
        String expectedAlias2 = "@guy1";
        Assertions.assertTrue(follows.get(1).getAlias().equals(expectedAlias2));
        String expectedAlias3 = "@carter";
        Assertions.assertTrue(follows.get(2).getAlias().equals(expectedAlias3));
    }

    @Test
    public void getFollows_should_returnNull_with_invalidRequest() {
        FollowResponse response = followDAO.getFollows(new FollowRequest(null, -1, null, false));
        Assertions.assertNull(response.getFollows());
    }

    @Test
    public void checkFollows_should_returnIsFollowing_when_following() {
        String alias = "@test";
        String viewedAlias = "@carter";
        UserFollowRequest request = new UserFollowRequest(alias, viewedAlias);
        UserFollowResponse response = followDAO.checkFollows(request);
        Assertions.assertTrue(response.getFollower());
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    public void checkFollows_should_returnBadResponse_with_invalidRequest() {
        UserFollowResponse response = followDAO.checkFollows(new UserFollowRequest(null, null));
        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    public void getFollowCount_should_returnGoodResponse_with_validRequest() {
        String alias = "@test";
        FollowCountRequest request = new FollowCountRequest(alias);
        FollowCountResponse response = followDAO.getFollowCount(request);
        Assertions.assertTrue(response.isSuccess());
        int expectedFolloweeCount = 2;
        Assertions.assertEquals(response.getFolloweeCount(), expectedFolloweeCount);
        int expectedFollowerCount = 3;
        Assertions.assertEquals(response.getFollowerCount(), expectedFollowerCount);
    }

    @Test
    public void getFollowCount_should_returnBadResponse_with_invalidRequest() {
        FollowCountResponse response = followDAO.getFollowCount(new FollowCountRequest(null));
        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    public void followStatus_should_switchStatus_with_goodRequest() {
        String alias = "@test";
        String viewedAlias = "@carter";
        UserFollowRequest request = new UserFollowRequest(alias, viewedAlias);
        UserFollowResponse response = followDAO.followStatus(request);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertFalse(response.getFollower());
        response = followDAO.followStatus(request);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertTrue(response.getFollower());
    }

    @Test
    public void followStatus_should_doNothing_with_badRequest() {
        UserFollowResponse response = followDAO.followStatus(new UserFollowRequest(null, null));
        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    public void putAndDelete_should_returnGoodResponse_with_goodRequest() {
        String alias = "@test";
        String viewedAlias = "@carter";
        UserFollowRequest request = new UserFollowRequest(alias, viewedAlias);
        UserFollowResponse response = followDAO.delete(request);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertFalse(response.getFollower());
        response = followDAO.put(request);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertTrue(response.getFollower());
    }

    @Test
    public void put_should_returnFailedResponse_with_badRequest() {
        UserFollowResponse response = followDAO.put(new UserFollowRequest(null, null));
        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    public void delete_should_returnFailedResponse_with_badRequest() {
        UserFollowResponse response = followDAO.delete(new UserFollowRequest(null, null));
        Assertions.assertFalse(response.isSuccess());
    }
}
