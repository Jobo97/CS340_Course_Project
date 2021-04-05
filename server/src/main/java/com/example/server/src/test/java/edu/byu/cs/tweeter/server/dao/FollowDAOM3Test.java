package com.example.server.src.test.java.edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.FollowDAOM3;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;

class FollowDAOM3Test {

    private final User user1 = new User("Daffy", "Duck", "");
    private final User user2 = new User("Fred", "Flintstone", "");
    private final User user3 = new User("Barney", "Rubble", "");
    private final User user4 = new User("Wilma", "Rubble", "");
    private final User user5 = new User("Clint", "Eastwood", "");
    private final User user6 = new User("Mother", "Teresa", "");
    private final User user7 = new User("Harriett", "Hansen", "");
    private final User user8 = new User("Zoe", "Zabriski", "");

    private FollowDAOM3 mFollowDAOM3Spy;

    @BeforeEach
    void setup() {
        mFollowDAOM3Spy = Mockito.spy(new FollowDAOM3());
    }

    @Test
    void testGetFollowees_noFolloweesForUser() {
        List<User> followees = Collections.emptyList();
        Mockito.when(mFollowDAOM3Spy.getDummyFollowees()).thenReturn(followees);

        FollowRequest request = new FollowRequest(user1.getAlias(), 10, null, true); // i think true
        FollowResponse response = mFollowDAOM3Spy.getFollows(request);

        Assertions.assertEquals(0, response.getFollows().size());
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFollowees_oneFollowerForUser_limitGreaterThanUsers() {
        List<User> followees = Collections.singletonList(user2);
        Mockito.when(mFollowDAOM3Spy.getDummyFollowees()).thenReturn(followees);

        FollowRequest request = new FollowRequest(user1.getAlias(), 10, null, true);
        FollowResponse response = mFollowDAOM3Spy.getFollows(request);

        Assertions.assertEquals(1, response.getFollows().size());
        Assertions.assertTrue(response.getFollows().contains(user2));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFollowees_twoFollowersForUser_limitEqualsUsers() {
        List<User> followees = Arrays.asList(user2, user3);
        Mockito.when(mFollowDAOM3Spy.getDummyFollowees()).thenReturn(followees);

        FollowRequest request = new FollowRequest(user3.getAlias(), 2, null, true);
        FollowResponse response = mFollowDAOM3Spy.getFollows(request);

        Assertions.assertEquals(2, response.getFollows().size());
        Assertions.assertTrue(response.getFollows().contains(user2));
        Assertions.assertTrue(response.getFollows().contains(user3));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFollowees_limitLessThanUsers_endsOnPageBoundary() {
        List<User> followees = Arrays.asList(user2, user3, user4, user5, user6, user7);
        Mockito.when(mFollowDAOM3Spy.getDummyFollowees()).thenReturn(followees);

        FollowRequest request = new FollowRequest(user5.getAlias(), 2, null, true);
        FollowResponse response = mFollowDAOM3Spy.getFollows(request);

        // Verify first page
        Assertions.assertEquals(2, response.getFollows().size());
        Assertions.assertTrue(response.getFollows().contains(user2));
        Assertions.assertTrue(response.getFollows().contains(user3));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify second page
        request = new FollowRequest(user5.getAlias(), 2, response.getFollows().get(1).getAlias(), true);
        response = mFollowDAOM3Spy.getFollows(request);

        Assertions.assertEquals(2, response.getFollows().size());
        Assertions.assertTrue(response.getFollows().contains(user4));
        Assertions.assertTrue(response.getFollows().contains(user5));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify third page
        request = new FollowRequest(user5.getAlias(), 2, response.getFollows().get(1).getAlias(), true);
        response = mFollowDAOM3Spy.getFollows(request);

        Assertions.assertEquals(2, response.getFollows().size());
        Assertions.assertTrue(response.getFollows().contains(user6));
        Assertions.assertTrue(response.getFollows().contains(user7));
        Assertions.assertFalse(response.getHasMorePages());
    }


    @Test
    void testGetFollowees_limitLessThanUsers_notEndsOnPageBoundary() {
        List<User> followees = Arrays.asList(user2, user3, user4, user5, user6, user7, user8);
        Mockito.when(mFollowDAOM3Spy.getDummyFollowees()).thenReturn(followees);

        FollowRequest request = new FollowRequest(user6.getAlias(), 2, null, true);
        FollowResponse response = mFollowDAOM3Spy.getFollows(request);

        // Verify first page
        Assertions.assertEquals(2, response.getFollows().size());
        Assertions.assertTrue(response.getFollows().contains(user2));
        Assertions.assertTrue(response.getFollows().contains(user3));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify second page
        request = new FollowRequest(user6.getAlias(), 2, response.getFollows().get(1).getAlias(), true);
        response = mFollowDAOM3Spy.getFollows(request);

        Assertions.assertEquals(2, response.getFollows().size());
        Assertions.assertTrue(response.getFollows().contains(user4));
        Assertions.assertTrue(response.getFollows().contains(user5));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify third page
        request = new FollowRequest(user6.getAlias(), 2, response.getFollows().get(1).getAlias(), true);
        response = mFollowDAOM3Spy.getFollows(request);

        Assertions.assertEquals(2, response.getFollows().size());
        Assertions.assertTrue(response.getFollows().contains(user6));
        Assertions.assertTrue(response.getFollows().contains(user7));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify fourth page
        request = new FollowRequest(user6.getAlias(), 2, response.getFollows().get(1).getAlias(), true);
        response = mFollowDAOM3Spy.getFollows(request);

        Assertions.assertEquals(1, response.getFollows().size());
        Assertions.assertTrue(response.getFollows().contains(user8));
        Assertions.assertFalse(response.getHasMorePages());
    }
}
