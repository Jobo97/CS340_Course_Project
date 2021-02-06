package edu.byu.cs.tweeter.model.net;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import edu.byu.cs.tweeter.BuildConfig;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {
    // This is the hard coded followee data returned by the 'getFollowees()' method
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

    private List<String> mention1 = new ArrayList<String>(){{
        add("@alias1");
        add("@alias2");
    }};
    private List<String> mention2 = new ArrayList<String>(){{
        add("@alias3");
        add("@alias4");
    }};

    private List<String> url1 = new ArrayList<String>(){{
        add("www.byu.edu");
        add("www.google.com");
    }};

    private List<String> url2 = new ArrayList<String>(){{
        add("www.apple.com");
        add("www.linkedin.com");
    }};

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date;

    {
        try {
            date = dateFormat.parse("23/09/2007");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    long time = date.getTime();
    //new Timestamp(time);
    private final Status status1 = new Status(mention1, url1, "Tweet from status1. @alias1 @alias2 www.byu.edu www.google.com",
            new Timestamp(time), user1);
    private final Status status2 = new Status(mention2, url2, "Tweet from status2. @alias3 @alias4 www.apple.com www.linked.com",
            new Timestamp(time), user2);
    private final Status status3 = new Status(mention1, url1, "Tweet from status3. @alias1 @alias2 www.byu.edu www.google.com",
            new Timestamp(time), user1);
    private final Status status4 = new Status(mention2, url2, "Tweet from status4. @alias3 @alias4 www.apple.com www.linked.com",
            new Timestamp(time), user2);


    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) {
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        return new LoginResponse(user, new AuthToken("Test_User"));
    }

    public StoryResponse getStory(StoryRequest request) {
        String alias = request.getUserAlias();
        //would need the user alias to get tweets, facade eliminates that need.
        List<Status> story = getDummyStory();
        return new StoryResponse(story, false);
    }

    public FeedResponse getFeed(FeedRequest request) {
        String alias = request.getUserAlias();
        //would need the user alias to get tweets of their followers, facade eliminates that need.
        List<Status> feed = getDummyFeed();
        return new FeedResponse(feed, false);
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the following response.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollowerAlias() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowees = getDummyFollowees();
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followeesIndex = getStartingIndex(request.getLastFolloweeAlias(), allFollowees);

            for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                responseFollowees.add(allFollowees.get(followeesIndex));
            }

            hasMorePages = followeesIndex < allFollowees.size();
        }

        return new FollowingResponse(responseFollowees, hasMorePages);
    }

    /**
     * Returns the users that follow the user specified in the request.
     *
     * @param request contains information about the users who follow the specified user and returns and any
     *                other information required to satisfy the request.
     * @return the following response.
     */
    public FollowerResponse getFollowers(FollowerRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollowerAlias() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowers = getDummyFollowers();
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followersIndex = getStartingIndex(request.getLastFollowerAlias(), allFollowers);

            for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                responseFollowers.add(allFollowers.get(followersIndex));
            }

            hasMorePages = followersIndex < allFollowers.size();
        }

        return new FollowerResponse(responseFollowers, hasMorePages);
    }

    /**
     * Determines the index for the first followee/follower in the specified 'allFollowees' or 'allFollowers' list that should
     * be returned in the current request. This will be the index of the next followee/follower after the
     * specified 'lastFollowee' 'lastFollower.
     *
     * @param lastAlias the alias of the last followee/follower that was returned in the previous
     *                          request or null if there was no previous request.
     * @param allUsers the generated list of followees/followers from which we are returning paged results.
     * @return the index of the first followee/follower to be returned.
     */
    private int getStartingIndex(String lastAlias, List<User> allUsers) {

        int index = 0;

        if(lastAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allUsers.size(); i++) {
                if(lastAlias.equals(allUsers.get(i).getAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    index = i + 1;
                    break;
                }
            }
        }

        return index;
    }

    /**
     * Returns the list of dummy followee data. This is written as a separate method to allow
     * mocking of the followees.
     *
     * @return the followees.
     */
    List<User> getDummyFollowees() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }

    List<User> getDummyFollowers() {
        return Arrays.asList(user2, user4, user6, user8, user10, user12, user14, user16, user18, user20);
    }

    List<Status> getDummyFeed() {
        return Arrays.asList(status2,status4);
    }

    List<Status> getDummyStory() {
        return Arrays.asList(status1,status3);
    }



    //Data Structures for users, another for follow objects (following/followers)
    //Create Requests/Responses (especially with the follower)
    //Story Service
}
