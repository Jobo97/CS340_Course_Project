package com.example.server.src.test.java.edu.byu.cs.tweeter.server.dao;

import com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao.S3DAO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class S3DAOTests {

    S3DAO s3DAO;
    byte[] imageBytes;
    String fileName;
    String imageURL;

    @BeforeEach
    public void setup() {
        s3DAO = new S3DAO();
        fileName = "Donald";
        imageURL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        try {
            imageBytes = ByteArrayUtils.bytesFromUrl(imageURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void putThenGet_returns_expectedURL_with_validImage() {
        s3DAO.putObject(imageBytes, fileName);
        String expectedURL = "https://cbstweeterprofileimages.s3.us-west-2.amazonaws.com/Donald";
        Assertions.assertTrue(s3DAO.getObject(fileName).equals(expectedURL));
    }

    @Test
    public void putThenGet_throws_with_invalidImage() {
        Assertions.assertThrows(NullPointerException.class, () -> s3DAO.putObject(null, null));
        String expectedURL = "https://cbstweeterprofileimages.s3.us-west-2.amazonaws.com/";
        Assertions.assertTrue(s3DAO.getObject(null).equals(expectedURL));
    }
}
