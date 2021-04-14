package com.example.server.src.main.java.edu.byu.cs.tweeter.server.dao;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class S3DAO {
    private byte[] imageBytes;
    private String fileName; // will end up being user_alias
    private String bucketName = "cbstweeterprofileimages";
    private AmazonS3 s3;

    public S3DAO() {

        try {
            s3 = AmazonS3ClientBuilder
                    .standard()
                    .withRegion("us-west-2")
                    .build();
        }
        catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }

    public void putObject(byte[] imageBytes, String fileName) {
        PutObjectRequest s3Request = new PutObjectRequest(
                bucketName,
                fileName,
                new ByteArrayInputStream(imageBytes),
                new ObjectMetadata());
        s3.putObject(s3Request.withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String getObject(String fileName) {
//        S3Object object = s3.getObject(bucketName, fileName);
//        InputStream is = object.getObjectContent();
        return (s3.getUrl(bucketName, fileName)).toString();
//        byte[] bytes = null;
//        try {
//            bytes = IOUtils.toByteArray(is);
//            object.getObjectContent();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bytes;
    }

}
