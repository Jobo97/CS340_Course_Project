package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.Status;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusFeedRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class FeedDAO {

    private final String TABLE_FEED = "feed";

    AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();

    private DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    private Table feedTable = dynamoDB.getTable(TABLE_FEED);
    private UserDAO uDao = new UserDAO();

    public StatusResponse getFeedPaginated(String userAlias, int pageSize, String lastTimestamp) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#ua", "user_alias");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":uav", userAlias);
        QuerySpec querySpec = new QuerySpec()
                .withScanIndexForward(false)
                .withKeyConditionExpression("#ua = :uav").withNameMap(nameMap)
                .withValueMap(valueMap)
                .withMaxResultSize(pageSize);
        if(lastTimestamp != null){       // Primary and the sort key
            querySpec.withExclusiveStartKey("user_alias", userAlias,
                    "timestamp", lastTimestamp);
        }

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;
        List<Status> statuses = new ArrayList<>();
        boolean hasMorePages = false;

        try {
            items = feedTable.query(querySpec);
//            while(items.getLastLowLevelResult() != null){
//                hasMorePages = true;
//                System.out.println("additional page");
//                querySpec = new QuerySpec()
//                        .withScanIndexForward(false)
//                        .withKeyConditionExpression("#ua = :uav").withNameMap(nameMap)
//                        .withValueMap(valueMap)
//                        .withMaxResultSize(pageSize)
//                        .withExclusiveStartKey((KeyAttribute) items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey());
//                items = feedTable.query(querySpec);
//            }

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                User u = uDao.get(item.getString("tweet_user_alias"));              //Review this.
                Status s = new Status(item.getString("tweet"), u, item.getString("timestamp"));
                statuses.add(s);
            }
            if(statuses.size() == pageSize){
                hasMorePages = true;
            }

        }
        catch (Exception e) {
            System.err.println("Unable to query " + userAlias + "'s story!");
            System.err.println(e.getMessage());
            return new StatusResponse(null, false);
        }
        return new StatusResponse(statuses,hasMorePages);
    }

    public boolean putFeed(PostStatusFeedRequest postStatusFeedRequest) {
        System.out.println("putFeed function in FeedDAO");
        System.out.println(postStatusFeedRequest.toString());
        try {
            // user.getAlias() is the person who will see it, alias is the person who tweeted it
            PutItemOutcome outcome = feedTable
                    .putItem(new Item()
                            .withPrimaryKey("user_alias", postStatusFeedRequest.getAlias(),
                                    "timestamp", postStatusFeedRequest.getTimeStampString())
                            .withString("tweet", postStatusFeedRequest.getTweet())
                            .withString("tweet_user_alias", postStatusFeedRequest.getTweet_alias()));
        } catch(Exception e) {
            System.err.println("Unable to add tweet to feed: " + postStatusFeedRequest.getTweet());
            return false;
        }
        return true;
    }
}
