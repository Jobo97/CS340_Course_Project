package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.UserFollowResponse;

/**
 * A DAO for accessing 'following' data from the database.
 */
public class FollowDAO {
//    private final User ben = new User("Ben", "Millett", "@benmillett", MALE_IMAGE_URL);
//    private final User michael = new User("Michael", "Skonnard", "@michaelskonnard", MALE_IMAGE_URL);
//    private final User carter = new User("Carter", "Wonnacott", "@carterwonnacott", MALE_IMAGE_URL);
//
//    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
//    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";
//
//    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
//    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
//    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
//    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
//    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
//    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
//    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
//    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
//    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
//    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
//    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
//    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
//    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
//    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
//    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
//    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
//    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
//    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
//    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
//    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);

    AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();

    private DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
    private Table table = dynamoDB.getTable("follows");
    private UserDAO uDao = new UserDAO();


    public Integer getFolloweeCount(String alias) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#fr", "follower_alias");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("#frv", alias);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#fr = #frv").withNameMap(nameMap)
                .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        items = table.query(querySpec);

        iterator = items.iterator();
        int followee_count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            followee_count++;
        }

        return followee_count;
    }

    public Integer getFollowerCount(String alias) {
        Index index = table.getIndex("followee_alias-follower_alias-index");

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#fe", "followee_alias");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("#fev", alias);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#fe = #fev").withNameMap(nameMap)
                .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        items = index.query(querySpec);

        iterator = items.iterator();
        int follower_count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            follower_count++;
        }
        return follower_count;
    }

    public FollowResponse getFollowersPaginated(String followee_alias, Integer pageSize) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#fe", "followee_alias");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("#fev", followee_alias);
        QuerySpec querySpec = new QuerySpec()
                .withScanIndexForward(false)
                .withKeyConditionExpression("#fe = #fev").withNameMap(nameMap)
                .withValueMap(valueMap)
                .withMaxResultSize(pageSize);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;
        List<User> users = new ArrayList<>();
        boolean hasMorePages = false;

        try {
            System.out.println(followee_alias + "'s followers:");
            items = table.getIndex("followee_alias-follower_alias-index").query(querySpec);
            while(items.getLastLowLevelResult() != null){
                hasMorePages = true;
                System.out.println("additional page");
                querySpec = new QuerySpec()
                        .withScanIndexForward(false)
                        .withKeyConditionExpression("#fe = #fev").withNameMap(nameMap)
                        .withValueMap(valueMap)
                        .withMaxResultSize(pageSize)
                        .withExclusiveStartKey((KeyAttribute) items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey());
                items = table.getIndex("followee_alias-follower_alias-index").query(querySpec);

            }

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                User u = uDao.get(item.getString("user_alias"));
                users.add(u);
            }

        }
        catch (Exception e) {
            System.err.println("Unable to query " + followee_alias + "'s followers!");
            System.err.println(e.getMessage());
            return new FollowResponse(null, false);
        }
        return new FollowResponse(users,hasMorePages);
    }

    public FollowResponse getFolloweesPaginated(String follower_alias, Integer pageSize) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#fr", "follower_alias");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("#frv", follower_alias);
        QuerySpec querySpec = new QuerySpec()
                .withScanIndexForward(true)
                .withKeyConditionExpression("#fr = #frv").withNameMap(nameMap)
                .withValueMap(valueMap)
                .withMaxResultSize(pageSize);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;
        List<User> users = new ArrayList<>();
        boolean hasMorePages = false;

        try {
            System.out.println(follower_alias + "'s followees:");
            items = table.query(querySpec);
            while(items.getLastLowLevelResult() != null){
                hasMorePages = true;
                System.out.println("additional page");
                querySpec = new QuerySpec()
                        .withScanIndexForward(true)
                        .withKeyConditionExpression("#fr = #frv").withNameMap(nameMap)
                        .withValueMap(valueMap)
                        .withMaxResultSize(pageSize)
                        .withExclusiveStartKey((KeyAttribute) items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey());
                items = table.getIndex("followee_alias-follower_alias-index").query(querySpec);
            }

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                User u = uDao.get(item.getString("user_alias"));
                users.add(u);
            }

        }
        catch (Exception e) {
            System.err.println("Unable to query " + follower_alias + "'s followees!");
            System.err.println(e.getMessage());
            return new FollowResponse(null, false);
        }
        return new FollowResponse(users, hasMorePages);
    }

    public FollowResponse getFollows(FollowRequest request) {

//        List<User> allFollows;
        if(request.getFollowerAlias() == null){
            return new FollowResponse(null, false);
        }
        if (request.getFollower()) {
            return getFollowersPaginated(request.getFollowerAlias(), request.getLimit());
        }
        else {
            return getFolloweesPaginated(request.getFollowerAlias(), request.getLimit());
        }
//
//        List<User> responseFollows = new ArrayList<>(request.getLimit());
//
//        boolean hasMorePages = false;
//
//        if(request.getLimit() > 0) {
//            int followIndex = getStartingIndex(request.getLastFolloweeAlias(), allFollows);
//
//            for(int limitCounter = 0; followIndex < allFollows.size() && limitCounter < request.getLimit(); followIndex++, limitCounter++) {
//                responseFollows.add(allFollows.get(followIndex));
//            }
//
//            hasMorePages = followIndex < allFollows.size();
//        }
//
//        return new FollowResponse(responseFollows, hasMorePages);
    }

//    private int getStartingIndex(String lastFolloweeAlias, List<User> allFollowees) {
//
//        int followeesIndex = 0;
//
//        if(lastFolloweeAlias != null) {
//            // This is a paged request for something after the first page. Find the first item
//            // we should return
//            for (int i = 0; i < allFollowees.size(); i++) {
//                if(lastFolloweeAlias.equals(allFollowees.get(i).getAlias())) {
//                    // We found the index of the last item returned last time. Increment to get
//                    // to the first one we should return
//                    followeesIndex = i + 1;
//                    break;
//                }
//            }
//        }
//
//        return followeesIndex;
//    }

    // checkFollows: for looking at and updating button
    public UserFollowResponse checkFollows(UserFollowRequest request) {
        //Deal with error throwing for invalid data
//        Random random = new Random();
//        if (request.getUserAlias() == null){
//            return new UserFollowResponse(false, random.nextBoolean());
//        }
//        return new UserFollowResponse(true, random.nextBoolean());
        try {
//            System.out.println(follower_handle + "'s followees:");
            Item item = table.getItem("follower_alias", request.getUserAlias(),
                    "followee_alias", request.getViewedAlias());
            if(item != null){
                return new UserFollowResponse(true, true);
            }
            else {
                return new UserFollowResponse(true, false);
            }
        }
        catch (Exception e) {
            System.err.println("Unable to query if " + request.getUserAlias() + " follows " + request.getViewedAlias());
            System.err.println(e.getMessage());
            return new UserFollowResponse(false, false);
        }
    }

    public FollowCountResponse getFollowCount(FollowCountRequest request) {
        //Ignore the request for dummy data only
        if(request.getUserAlias() == null){
            return new FollowCountResponse(false, 0, 0);
        }
        return new FollowCountResponse(true, getFollowerCount(request.getUserAlias()), getFolloweeCount(request.getUserAlias()));
    }

    // followStatus changing your follow status for that person
    public UserFollowResponse followStatus(UserFollowRequest request) {
        if(request.getUserAlias() == null || request.getViewedAlias() == null){
            return new UserFollowResponse(false, false);
        }
        UserFollowResponse userFollowResponse = checkFollows(request);
        if(userFollowResponse.isSuccess()) {
            if (checkFollows(request).getFollower()) {
                return delete(request);
            } else {
                return put(request);
            }
        }
        else{
            return new UserFollowResponse(false, false);
        }
    }

    public UserFollowResponse put(UserFollowRequest request){
        final Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("follower_name", request.getUserAlias());
        infoMap.put("followee_name", request.getViewedAlias());


        try {
            System.out.println("Adding a new item...");
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("follower_alias", request.getUserAlias(),
                            "followee_alias", request.getViewedAlias()).withMap("info", infoMap));

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
            return new UserFollowResponse(true, true);

        }
        catch (Exception e) {
            System.err.println("Unable to put that " + request.getUserAlias() + " follows " + request.getViewedAlias());
            System.err.println(e.getMessage());
            return new UserFollowResponse(false, false);
        }
    }

    public UserFollowResponse delete(UserFollowRequest request){
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("follower_alias", request.getUserAlias(),
                        "followee_alias", request.getViewedAlias()));


        try {
            System.out.println("Attempting a conditional delete...");
            table.deleteItem(deleteItemSpec);
            System.out.println("DeleteItem succeeded");
            return new UserFollowResponse(true, false);
        }
        catch (Exception e) {
            System.err.println("Unable to delete item: " + request.getUserAlias() + " still follows " + request.getViewedAlias());
            System.err.println(e.getMessage());
            return new UserFollowResponse(false, false);
        }
    }

//    public List<User> getDummyFollowees() {
//        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
//                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
//                user19, user20);
//    }
//    List<User> getDummyFollowers() {
//        return Arrays.asList(michael, user2, user4, user6, user8, user10, user12, user14, user16, user18, user20);
//    }

}


//DynamoDB object
//        object.get(request.getUserAlias)
//
//        // Used in place of assert statements because Android does not support them
////        if(BuildConfig.DEBUG) {
////            if(request.getLimit() < 0) {
////                throw new AssertionError();
////            }
////
////            if(request.getFollowerAlias() == null) {
////                throw new AssertionError();
////            }
////        }
//
//        List<User> allFollows;
//        if (request.getFollower()) {
//            allFollows = getDummyFollowers();
//        }
//        else {
//            allFollows = getDummyFollowees();
//        }
//
//        List<User> responseFollows = new ArrayList<>(request.getLimit());
//
//        boolean hasMorePages = false;
//
//        if(request.getLimit() > 0) {
//            int followIndex = getStartingIndex(request.getLastFolloweeAlias(), allFollows);
//
//            for(int limitCounter = 0; followIndex < allFollows.size() && limitCounter < request.getLimit(); followIndex++, limitCounter++) {
//                responseFollows.add(allFollows.get(followIndex));
//            }
//
//            hasMorePages = followIndex < allFollows.size();
//        }
//
//        return new FollowResponse(responseFollows, hasMorePages);
