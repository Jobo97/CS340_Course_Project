package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Status {
    private List<String> Mentions;
    private List<String> Urls;
    private String tweet;
    private Timestamp timeStamp;            //Review this Timestamp class
    private User user;

    public Status(String tweet, Timestamp timeStamp, User user) {
        this.Mentions = parseMentions(tweet);
        this.Urls = parseURLS(tweet);
        this.tweet = tweet;
        this.timeStamp = timeStamp;
        this.user = user;
    }

    public List<String> getMentions() {
        return Mentions;
    }

    public List<String> getUrls() {
        return Urls;
    }

    public String getTweet() {
        return tweet;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return tweet.equals(status.tweet) && timeStamp.equals(status.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tweet) + Objects.hash(timeStamp);
    }

    @Override
    public String toString() {
        return "Status{" +
                "Mentions='" + Mentions.toString() + '\'' +
                ", Urls='" + Urls.toString() + '\'' +
                ", tweet='" + tweet + '\'' +
                ", timeStamp='" + timeStamp.toString() + '\'' +
                ", user='" + user.toString() + '\'' +
                '}';
    }

    private List<String> parseMentions(String tweet){
        String[] splitTweet = tweet.split("\\s+");
        List<String> mentions = new ArrayList<>();
        for(String word : splitTweet){
            if(word.charAt(0) == '@'){
                mentions.add(word);
            }
        }
        return mentions;
    }

    private List<String> parseURLS(String tweet){
        String[] splitTweet = tweet.split("\\s+");
        List<String> urls = new ArrayList<>();
        for(String word : splitTweet){
            if(word.contains("www")){
                urls.add(word);
            }
        }
        return urls;
    }


}
