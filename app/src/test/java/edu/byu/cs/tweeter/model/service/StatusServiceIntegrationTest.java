package edu.byu.cs.tweeter.model.service;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.Status;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.StatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.StatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StatusServiceIntegrationTest {

    StatusServiceProxy statusServiceProxy;

    StatusRequest statusRequest;
    StatusRequest invalidRequest;

    StatusResponse expectedResponse;
    StatusResponse invalidResponse;

    Date date;
    Date date2;

    @BeforeEach
    public void setup() {
        String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";
        User ben = new User("Ben", "Millett", "@benmillett", MALE_IMAGE_URL);
        User michael = new User("Michael", "Skonnard", "@michaelskonnard", MALE_IMAGE_URL);
        User carter = new User("Carter", "Wonnacott", "@carterwonnacott", MALE_IMAGE_URL);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        try {
            date = dateFormat.parse("23/09/2007 05:05:05");
            date2 = dateFormat.parse("24/09/2007 11:11:11");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = date.getTime();
        long time2 = date2.getTime();

        Status status1 = new Status("Tweet from 1. @michaelskonnard @carterwonnacott www.byu.edu www.google.com",
                ben,(new Timestamp(time)).toString());
        Status status2 = new Status("Tweet from 2. @benmillett @carterwonnacott www.apple.com www.linked.com",
                michael,(new Timestamp(time2)).toString());
//        Status status3 = new Status("Tweet from 3. @michaelskonnard @benmillett www.byu.edu www.google.com",
//                carter,(new Timestamp(time)).toString());
//        Status status4 = new Status("Tweet from 4. @benmillett @michaelskonnard www.apple.com www.linked.com",
//                carter,(new Timestamp(time2)).toString());
        Status status5 = new Status("Tweet from 5. @benmillett @carterwonnacott www.apple.com www.linked.com",
                michael,(new Timestamp(time2)).toString());
//        Status status6 = new Status("Tweet from 6. @michaelskonnard @benmillett www.byu.edu www.google.com",
//                carter,(new Timestamp(time)).toString());
        Status status7 = new Status("Tweet from 7. @benmillett @carterwonnacott www.apple.com www.linked.com",
                michael,(new Timestamp(time2)).toString());
//        Status status8 = new Status("Tweet from 8. @michaelskonnard @benmillett www.byu.edu www.google.com",
//                carter,((new Timestamp(time2)).toString()));
//        Status status9 = new Status("Tweet from 9. @michaelskonnard @benmillett www.byu.edu www.google.com",
//                carter,(new Timestamp(time)).toString());
//        Status status10 = new Status("Tweet from 10. @michaelskonnard @benmillett www.byu.edu www.google.com",
//                carter,(new Timestamp(time)).toString());
//        Status status11 = new Status("Tweet from 11. @michaelskonnard @benmillett www.byu.edu www.google.com",
//                carter,(new Timestamp(time)).toString());
//        Status status12 = new Status("Tweet from 12. @michaelskonnard @benmillett www.byu.edu www.google.com",
//                carter,(new Timestamp(time)).toString());
//        Status status13 = new Status("Tweet from 13. @michaelskonnard @benmillett www.byu.edu www.google.com",
//                carter,(new Timestamp(time)).toString());
        Status status14 = new Status("Tweet from 14. @benmillett @carterwonnacott www.apple.com www.linked.com",
                michael,(new Timestamp(time2)).toString());
        Status status15 = new Status("Tweet from 15. @benmillett @carterwonnacott www.apple.com www.linked.com",
                michael,(new Timestamp(time2)).toString());
        Status status16 = new Status("Tweet from 16. @benmillett @carterwonnacott www.apple.com www.linked.com",
                michael,(new Timestamp(time2)).toString());
        Status status17 = new Status("Tweet from 17. @benmillett @carterwonnacott www.apple.com www.linked.com",
                michael,(new Timestamp(time2)).toString());
        Status status18 = new Status("Tweet from 18. @benmillett @carterwonnacott www.apple.com www.linked.com",
                michael,(new Timestamp(time2)).toString());

        // setup request
        statusRequest = new StatusRequest("@carterwonnacott", 10, "", false);
        invalidRequest = new StatusRequest("", 0, "", false);
        // setup expected response
        expectedResponse = new StatusResponse(new ArrayList<>(), true);

        expectedResponse.getStatuses().add(status18);
        expectedResponse.getStatuses().add(status17);
        expectedResponse.getStatuses().add(status16);
        expectedResponse.getStatuses().add(status15);
        expectedResponse.getStatuses().add(status14);
        expectedResponse.getStatuses().add(status7);
        expectedResponse.getStatuses().add(status5);
        expectedResponse.getStatuses().add(status2);
        expectedResponse.getStatuses().add(status1);

        invalidResponse = new StatusResponse(null, false);
        // setup service
        statusServiceProxy = new StatusServiceProxy();
    }

    @Test
    public void testGetStatuses_validRequest_correctResponse() throws Exception {
        StatusResponse response = statusServiceProxy.getStatuses(statusRequest);
        Assertions.assertEquals(response, expectedResponse);
    }

    @Test
    public void testGetStatuses_invalidRequest_incorrectResponse() throws Exception {
        StatusResponse response = statusServiceProxy.getStatuses(invalidRequest);
        Assertions.assertEquals(response, invalidResponse);
    }

}
