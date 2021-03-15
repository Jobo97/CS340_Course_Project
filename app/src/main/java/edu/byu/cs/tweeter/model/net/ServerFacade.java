package edu.byu.cs.tweeter.model.net;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import edu.byu.cs.tweeter.BuildConfig;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.Status;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LoginRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.UserFollowResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.LoginResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {
    // This is the hard coded followee data returned by the 'getFollowees()' method
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User ben = new User("Ben", "Millett", "@benmillett", MALE_IMAGE_URL);
    private final User michael = new User("Michael", "Skonnard", "@michaelskonnard", MALE_IMAGE_URL);
    private final User carter = new User("Carter", "Wonnacott", "@carterwonnacott", MALE_IMAGE_URL);

    private User registeredUser;
    private int counter;

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

    private Map<String, String> databaseUsernamePassword = new HashMap<String, String>(){{
        put(ben.getAlias(), "password-ben");
        put(michael.getAlias(), "password-michael");
        put(carter.getAlias(), "password-carter");
    }};

    private Map<String, User> databaseUsernameUser = new HashMap<String, User>(){{
        put(ben.getAlias(), ben);
        put(michael.getAlias(), michael);
        put(carter.getAlias(), carter);
    }};

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    Date date;
    Date date2;

    {
        try {
            date = dateFormat.parse("23/09/2007 05:05:05");
            date2 = dateFormat.parse("24/09/2007 11:11:11");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    long time = date.getTime();
    long time2 = date2.getTime();

    private final Status status1 = new Status("Tweet from 1. @michaelskonnard @carterwonnacott www.byu.edu www.google.com",
            new Timestamp(time), ben);
    private final Status status2 = new Status("Tweet from 2. @benmillett @carterwonnacott www.apple.com www.linked.com",
            new Timestamp(time2), michael);
    private final Status status3 = new Status("Tweet from 3. @michaelskonnard @benmillett www.byu.edu www.google.com",
            new Timestamp(time), carter);
    private final Status status4 = new Status("Tweet from 4. @benmillett @michaelskonnard www.apple.com www.linked.com",
            new Timestamp(time2), carter);
    private final Status status5 = new Status("Tweet from 5. @benmillett @carterwonnacott www.apple.com www.linked.com",
            new Timestamp(time2), michael);
    private final Status status6 = new Status("Tweet from 6. @michaelskonnard @benmillett www.byu.edu www.google.com",
            new Timestamp(time), carter);
    private final Status status7 = new Status("Tweet from 7. @benmillett @carterwonnacott www.apple.com www.linked.com",
            new Timestamp(time2), michael);
    private final Status status8 = new Status("Tweet from 8. @michaelskonnard @benmillett www.byu.edu www.google.com",
            new Timestamp(time), carter);
    private final Status status9 = new Status("Tweet from 9. @michaelskonnard @benmillett www.byu.edu www.google.com",
            new Timestamp(time), carter);
    private final Status status10 = new Status("Tweet from 10. @michaelskonnard @benmillett www.byu.edu www.google.com",
            new Timestamp(time), carter);
    private final Status status11 = new Status("Tweet from 11. @michaelskonnard @benmillett www.byu.edu www.google.com",
            new Timestamp(time), carter);
    private final Status status12 = new Status("Tweet from 12. @michaelskonnard @benmillett www.byu.edu www.google.com",
            new Timestamp(time), carter);
    private final Status status13 = new Status("Tweet from 13. @michaelskonnard @benmillett www.byu.edu www.google.com",
            new Timestamp(time), carter);
    private final Status status14 = new Status("Tweet from 14. @benmillett @carterwonnacott www.apple.com www.linked.com",
            new Timestamp(time2), michael);
    private final Status status15 = new Status("Tweet from 15. @benmillett @carterwonnacott www.apple.com www.linked.com",
            new Timestamp(time2), michael);
    private final Status status16 = new Status("Tweet from 16. @benmillett @carterwonnacott www.apple.com www.linked.com",
            new Timestamp(time2), michael);
    private final Status status17 = new Status("Tweet from 17. @benmillett @carterwonnacott www.apple.com www.linked.com",
            new Timestamp(time2), michael);
    private final Status status18 = new Status("Tweet from 18. @benmillett @carterwonnacott www.apple.com www.linked.com",
            new Timestamp(time2), michael);


    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) {
        if (request.isRegister()) {
            registeredUser = new User("firstname", "lastname", "username", MALE_IMAGE_URL);
            return new LoginResponse(registeredUser, new AuthToken("New_User"));
        }
        return new LoginResponse(carter, new AuthToken("Carter_Token"));
    }

    public StatusResponse getStatuses(StatusRequest request) {
        List<Status> statuses;
        if (request.isStory()) {
            statuses = getDummyStory();
        }
        else {
            statuses = getDummyFeed();
        }


        Collections.sort(statuses, new Comparator<Status>() {
            @Override
            public int compare(Status o1, Status o2) {
                try {
                    return o1.getTimeStamp().compareTo(o2.getTimeStamp());
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
        Collections.reverse(statuses);

        List<Status> responseStatus = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followIndex = getStartingIndexStatus(request.getLastStatus(), statuses);

            for(int limitCounter = 0; followIndex < statuses.size() && limitCounter < request.getLimit(); followIndex++, limitCounter++) {
                responseStatus.add(statuses.get(followIndex));
            }

            hasMorePages = followIndex < statuses.size();
        }
        return new StatusResponse(responseStatus, hasMorePages);
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
    public FollowResponse getFollows(FollowRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollowerAlias() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollows;
        if (request.isFollower()) {
            allFollows = getDummyFollowers();
        }
        else {
            allFollows = getDummyFollowees();
        }

        List<User> responseFollows = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followIndex = getStartingIndex(request.getLastFolloweeAlias(), allFollows);

            for(int limitCounter = 0; followIndex < allFollows.size() && limitCounter < request.getLimit(); followIndex++, limitCounter++) {
                responseFollows.add(allFollows.get(followIndex));
            }

            hasMorePages = followIndex < allFollows.size();
        }

        return new FollowResponse(responseFollows, hasMorePages);
    }

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

    private int getStartingIndexStatus(String tweet, List<Status> allStatus) {

        int index = 0;

        if(tweet != null) {
            for (int i = 0; i < allStatus.size(); i++) {
                if(tweet.equals(allStatus.get(i).getTweet())) {
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
        return Arrays.asList(ben, user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }

    List<User> getDummyFollowers() {
        return Arrays.asList(michael, user2, user4, user6, user8, user10, user12, user14, user16, user18, user20);
    }

    List<Status> getDummyFeed() {
        return Arrays.asList(status1,status2,status5,status7,status14,status15,status16,status17,status18);
    }

    List<Status> getDummyStory() {
        return Arrays.asList(status3,status4,status6,status8,status9,status10,status11,status12,status13);
    }

    public Response postStatus(PostStatusRequest request) {
        //would add the tweet to the data base
        return new Response(true);
    }

    public UserFollowResponse checkFollows(UserFollowRequest request) {
        //Deal with error throwing for invalid data
        Random random = new Random();
        return new UserFollowResponse(true, random.nextBoolean());
    }

    public FollowCountResponse getFollowCount(FollowCountRequest request) {
        //Ignore the request for dummy data only
        return new FollowCountResponse(true, getDummyFollowers().size(), getDummyFollowees().size());
    }

    public UserFollowResponse followStatus(UserFollowRequest request) {
        Random random = new Random();
        return new UserFollowResponse(true, random.nextBoolean());
    }

    public GetUserResponse getUser(GetUserRequest request) {
        GetUserResponse getUserResponse = new GetUserResponse(true, databaseUsernameUser.get(request.getUseralias()));
        return getUserResponse;
    }

    public Response logout(LogoutRequest request) {
        //We will ignore the request since this is dummy data
        return new Response(true, "Logout worked");
    }


    //Data Structures for users, another for follow objects (following/followers)
    //Create Requests/Responses (especially with the follower)
    //Story Service
}
