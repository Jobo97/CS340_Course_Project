package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.Status;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatusDAO {

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User ben = new User("Ben", "Millett", "@benmillett", MALE_IMAGE_URL);
    private final User michael = new User("Michael", "Skonnard", "@michaelskonnard", MALE_IMAGE_URL);
    private final User carter = new User("Carter", "Wonnacott", "@carterwonnacott", MALE_IMAGE_URL);

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


    private Status status1 = new Status("Tweet from 1. @michaelskonnard @carterwonnacott www.byu.edu www.google.com",
            ben,(new Timestamp(time)).toString());
    private Status status2 = new Status("Tweet from 2. @benmillett @carterwonnacott www.apple.com www.linked.com",
            michael,(new Timestamp(time2)).toString());
    private Status status3 = new Status("Tweet from 3. @michaelskonnard @benmillett www.byu.edu www.google.com",
            carter,(new Timestamp(time)).toString());
    private Status status4 = new Status("Tweet from 4. @benmillett @michaelskonnard www.apple.com www.linked.com",
            carter,(new Timestamp(time2)).toString());
    private Status status5 = new Status("Tweet from 5. @benmillett @carterwonnacott www.apple.com www.linked.com",
            michael,(new Timestamp(time2)).toString());
    private Status status6 = new Status("Tweet from 6. @michaelskonnard @benmillett www.byu.edu www.google.com",
            carter,(new Timestamp(time)).toString());
    private Status status7 = new Status("Tweet from 7. @benmillett @carterwonnacott www.apple.com www.linked.com",
            michael,(new Timestamp(time2)).toString());
    private Status status8 = new Status("Tweet from 8. @michaelskonnard @benmillett www.byu.edu www.google.com",
            carter,((new Timestamp(time2)).toString()));
    private Status status9 = new Status("Tweet from 9. @michaelskonnard @benmillett www.byu.edu www.google.com",
            carter,(new Timestamp(time)).toString());
    private Status status10 = new Status("Tweet from 10. @michaelskonnard @benmillett www.byu.edu www.google.com",
            carter,(new Timestamp(time)).toString());
    private Status status11 = new Status("Tweet from 11. @michaelskonnard @benmillett www.byu.edu www.google.com",
            carter,(new Timestamp(time)).toString());
    private Status status12 = new Status("Tweet from 12. @michaelskonnard @benmillett www.byu.edu www.google.com",
            carter,(new Timestamp(time)).toString());
    private Status status13 = new Status("Tweet from 13. @michaelskonnard @benmillett www.byu.edu www.google.com",
            carter,(new Timestamp(time)).toString());
    private Status status14 = new Status("Tweet from 14. @benmillett @carterwonnacott www.apple.com www.linked.com",
            michael,(new Timestamp(time2)).toString());
    private Status status15 = new Status("Tweet from 15. @benmillett @carterwonnacott www.apple.com www.linked.com",
            michael,(new Timestamp(time2)).toString());
    private Status status16 = new Status("Tweet from 16. @benmillett @carterwonnacott www.apple.com www.linked.com",
            michael,(new Timestamp(time2)).toString());
    private Status status17 = new Status("Tweet from 17. @benmillett @carterwonnacott www.apple.com www.linked.com",
            michael,(new Timestamp(time2)).toString());
    private Status status18 = new Status("Tweet from 18. @benmillett @carterwonnacott www.apple.com www.linked.com",
            michael,(new Timestamp(time2)).toString());


    private final String TABLE_FOLLOWS = "follows";
    private final String TABLE_STORY = "story";
    private final String TABLE_FEED = "feed";

    AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();

    private DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
    private Table followsTable = dynamoDB.getTable("follows");
    private Table storyTable = dynamoDB.getTable("story");
    private Table feedTable = dynamoDB.getTable("feed");
    private UserDAO uDao = new UserDAO();
    private FollowDAO followDAO = new FollowDAO();

    public StatusResponse getStatuses(StatusRequest request) {
        List<Status> statuses;
        if (request.getStory()) {
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

    List<Status> getDummyFeed() {
        return Arrays.asList(status1,status2,status5,status7,status14,status15,status16,status17,status18);
    }

    List<Status> getDummyStory() {
        return Arrays.asList(status3,status4,status6,status8,status9,status10,status11,status12,status13);
    }

    public boolean putStory(String alias, String tweet, String timeStampString) {
        // NEEDS TIMESTAMP AS SORT KEY
        // When typing the tweet and post tweet is pressed, we need to make a timestamp and
        // include that timestamp in the PostStatusRequest, that way it is stored in the
        final Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("tweet", tweet);
        //infoMap.put("imageEncoded", followee_name);

        try {
            PutItemOutcome outcome = storyTable
                    .putItem(new Item()
                            .withPrimaryKey("user_alias", alias, "timestamp", timeStampString)
                            .withMap("info", infoMap));
        } catch(Exception e) {
            System.err.println("Unable to add tweet to story: " + tweet);
            return false;
        }
        return true;

    }

    public boolean putFeed(String alias, String tweet, String timeStampString) {
        // Need to get the list of the followers of the alias
        final Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("tweet", tweet);
        //infoMap.put("imageEncoded", followee_name);

        try {
            List<User> followers = followDAO.getFollowers(alias);     // We don't want it to be paginated
            if(followers == null){
                return false;
            }
            for (User user : followers) {
                infoMap.put("tweet", tweet);
                infoMap.put("tweet_user_alias", alias);
                // user.getAlias() is the person who will see it, alias is the person who tweeted it
                PutItemOutcome outcome = feedTable
                        .putItem(new Item()
                                .withPrimaryKey("user_alias", user.getAlias(), "timestamp", timeStampString)
                                .withMap("info", infoMap));
            }
        } catch(Exception e) {
            System.err.println("Unable to add tweet to feed: " + tweet);
            return false;
        }
        return true;
    }
}
