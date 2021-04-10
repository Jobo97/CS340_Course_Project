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
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class StoryDAO {
    private final String TABLE_STORY = "story";

    AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();

    private DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
    private Table storyTable = dynamoDB.getTable(TABLE_STORY);
    private UserDAO uDao = new UserDAO();

    public StatusResponse getStoryPaginated(String userAlias, int pageSize) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#ua", "user_alias");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":uav", userAlias);
        QuerySpec querySpec = new QuerySpec()
                .withScanIndexForward(false)
                .withKeyConditionExpression("#ua = :uav").withNameMap(nameMap)
                .withValueMap(valueMap)
                .withMaxResultSize(pageSize);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;
        List<Status> statuses = new ArrayList<>();
        boolean hasMorePages = false;

        try {
            items = storyTable.query(querySpec);
            while(items.getLastLowLevelResult() != null){
                hasMorePages = true;
                System.out.println("additional page");
                querySpec = new QuerySpec()
                        .withScanIndexForward(false)
                        .withKeyConditionExpression("#ua = :uav").withNameMap(nameMap)
                        .withValueMap(valueMap)
                        .withMaxResultSize(pageSize)
                        .withExclusiveStartKey((KeyAttribute) items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey());
                items = storyTable.query(querySpec);
            }

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                User u = uDao.get(item.getString("user_alias"));
                Status s = new Status(item.getString("tweet"), u, item.getString("timestamp"));
                statuses.add(s);
            }

        }
        catch (Exception e) {
            System.err.println("Unable to query " + userAlias + "'s story!");
            System.err.println(e.getMessage());
            return new StatusResponse(null, false);
        }
        return new StatusResponse(statuses,hasMorePages);
    }

    public boolean putStory(String alias, String tweet, String timeStampString) {
        // NEEDS TIMESTAMP AS SORT KEY
        // When typing the tweet and post tweet is pressed, we need to make a timestamp and
        // include that timestamp in the PostStatusRequest, that way it is stored in the
        try {
            PutItemOutcome outcome = storyTable
                    .putItem(new Item()
                            .withPrimaryKey("user_alias", alias, "timestamp", timeStampString)
                            .withString("tweet", tweet));
        } catch(Exception e) {
            System.err.println("Unable to add tweet to story: " + tweet);
            return false;
        }
        return true;

    }
}
