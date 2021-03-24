package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

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
import java.util.List;

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
}
