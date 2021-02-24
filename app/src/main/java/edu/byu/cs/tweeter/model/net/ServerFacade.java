package edu.byu.cs.tweeter.model.net;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import edu.byu.cs.tweeter.BuildConfig;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import edu.byu.cs.tweeter.model.service.response.UserFollowResponse;
import edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

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

    private final Status status1 = new Status("Tweet from status1. @michaelskonnard @carterwonnacott www.byu.edu www.google.com",
            new Timestamp(time), ben);
    private final Status status2 = new Status("Tweet from status2. @benmillett @carterwonnacott www.apple.com www.linked.com",
            new Timestamp(time2), michael);
    private final Status status3 = new Status("Tweet from carter #1. @michaelskonnard @benmillett www.byu.edu www.google.com",
            new Timestamp(time), carter);
    private final Status status4 = new Status("Tweet from carter #2. @benmillett @michaelskonnard www.apple.com www.linked.com",
            new Timestamp(time2), carter);
    private Status newStatus;


    private Map<String, List<Status>> databaseUsernameStatus = new HashMap<String, List<Status>>(){{
        put(ben.getAlias(), new ArrayList<Status>(){{
            add(status1);
        }});
        put(michael.getAlias(), new ArrayList<Status>(){{
            add(status2);
        }});
        put(carter.getAlias(), new ArrayList<Status>(){{
            add(status3);
            add(status4);
        }});
    }};

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
        String alias = carter.getAlias();
        List<Status> statuses = new ArrayList<>();
        if(request.isStory()){
            statuses.addAll(getDummyStory());
            if(newStatus != null){
                statuses.add(newStatus);
            }
        }
        else{
            for(String key : databaseUsernameStatus.keySet()){
                if (key.equals(alias)){
                    continue;
                }
                else{
                    statuses.addAll(getDummyFeed());
                }
            }
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
        return new StatusResponse(statuses, false);
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
        return Arrays.asList(status1,status2);
    }

    List<Status> getDummyStory() {
        return Arrays.asList(status3,status4);
    }

    public Response postStatus(PostStatusRequest request) {
        long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);
        Status status = new Status("This is a standard tweet!", timestamp, carter);
        newStatus = status;
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
