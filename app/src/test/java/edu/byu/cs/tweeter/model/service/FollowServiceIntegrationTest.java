package edu.byu.cs.tweeter.model.service;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.UserFollowResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.net.ServerFacade_Old;

public class FollowServiceIntegrationTest {

    private final User ben = new User("Ben", "Millett", "@benmillett", MALE_IMAGE_URL);
    private final User michael = new User("Michael", "Skonnard", "@michaelskonnard", MALE_IMAGE_URL);
    private final User carter = new User("Carter", "Wonnacott", "@carterwonnacott", MALE_IMAGE_URL);

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);

    FollowServiceProxy followServiceProxy;

    FollowRequest followRequest;
    FollowRequest invalidFollowRequest;

    UserFollowRequest userFollowRequest;
    UserFollowRequest invalidUserFollowRequest;

    FollowCountRequest followCountRequest;
    FollowCountRequest invalidFollowCountRequest;

    FollowResponse expectedFollowResponse;
    FollowResponse invalidFollowResponse;

    UserFollowResponse expectedUserFollowResponse;
    UserFollowResponse invalidUserFollowResponse;

    FollowCountResponse expectedFollowCountResponse;
    FollowCountResponse invalidFollowCountResponse;

    @BeforeEach
    public void setup() {
        followRequest = new FollowRequest("@carterwonnacott", 10, "@michaelskonnard", true);
        invalidFollowRequest = new FollowRequest(null, 0, null, false);

        userFollowRequest = new UserFollowRequest("@carterwonnacott", "@michaelskonnard");
        invalidUserFollowRequest = new UserFollowRequest(null, null);

        followCountRequest = new FollowCountRequest("@carterwonnacott");
        invalidFollowCountRequest = new FollowCountRequest(null);
        expectedFollowResponse = new FollowResponse(new ArrayList<>(), true);
        expectedFollowResponse.getFollows().add(user2);
        expectedFollowResponse.getFollows().add(user4);
        expectedFollowResponse.getFollows().add(user6);
        expectedFollowResponse.getFollows().add(user8);
        expectedFollowResponse.getFollows().add(user10);
        expectedFollowResponse.getFollows().add(user12);
        expectedFollowResponse.getFollows().add(user14);
        expectedFollowResponse.getFollows().add(user16);
        expectedFollowResponse.getFollows().add(user18);
        expectedFollowResponse.getFollows().add(user20);
        invalidFollowResponse = new FollowResponse(null, false);

        expectedUserFollowResponse = new UserFollowResponse(true, true);
        invalidUserFollowResponse = new UserFollowResponse(false, false);

        expectedFollowCountResponse = new FollowCountResponse(true, 11, 20);
        invalidFollowCountResponse = new FollowCountResponse(false, 0, 0);

        followServiceProxy = new FollowServiceProxy();
    }

    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = followServiceProxy.getFollows(followRequest);
        Assertions.assertEquals(expectedFollowResponse, response);
    }

    @Test
    public void testGetFollowees_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FollowResponse response = followServiceProxy.getFollows(followRequest);

        for(User user : response.getFollows()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }

    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowees() throws IOException, TweeterRemoteException {
        FollowResponse response = followServiceProxy.getFollows(invalidFollowRequest);
        Assertions.assertEquals(invalidFollowResponse, response);
    }

    @Test
    public void testGetFollowCount_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowCountResponse response = followServiceProxy.getFollowCount(followCountRequest);
        boolean areTrue = response.getFolloweeCount() == expectedFollowCountResponse.getFolloweeCount();
        areTrue = response.getFollowerCount() == expectedFollowCountResponse.getFollowerCount() && areTrue;
        areTrue = response.isSuccess() == expectedFollowCountResponse.isSuccess() && areTrue;
        Assertions.assertTrue(areTrue);
    }

    @Test
    public void testGetFollowCount_invalidRequest_returnsNoFollowees() throws IOException, TweeterRemoteException {
        FollowCountResponse response = followServiceProxy.getFollowCount(invalidFollowCountRequest);
        boolean areTrue = response.getFolloweeCount() == invalidFollowCountResponse.getFolloweeCount();
        areTrue = response.getFollowerCount() == invalidFollowCountResponse.getFollowerCount() && areTrue;
        areTrue = response.isSuccess() == invalidFollowCountResponse.isSuccess() && areTrue;
        Assertions.assertTrue(areTrue);
    }

    @Test
    public void testCheckFollow_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        UserFollowResponse response = followServiceProxy.checkFollow(userFollowRequest);
        Assertions.assertEquals(response.isSuccess(), expectedUserFollowResponse.isSuccess());
    }

    @Test
    public void testCheckFollow_invalidRequest_returnsNoFollowees() throws IOException, TweeterRemoteException {
        UserFollowResponse response = followServiceProxy.checkFollow(invalidUserFollowRequest);
        Assertions.assertEquals(invalidUserFollowResponse.isSuccess(), response.isSuccess());
    }

}
