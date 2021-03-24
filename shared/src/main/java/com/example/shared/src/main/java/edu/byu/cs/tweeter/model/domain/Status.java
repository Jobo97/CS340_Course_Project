package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Status {
    private List<String> mentions;
    private List<String> urls;
    private String tweet;
    private Timestamp timeStamp;            //Review this Timestamp class
    private String timeStampString;
    private User user;

    public Status() {}

    public Status(String tweet, Timestamp timeStamp, User user) {
        this.mentions = parseMentions(tweet);
        this.urls = parseURLS(tweet);
        this.tweet = tweet;
        this.timeStamp = timeStamp;
        if (timeStamp != null)
        {
            this.timeStampString = timeStamp.toString();
        }
        this.user = user;
    }

    public Status(String tweet, User user, String timeStamp) {
        this.mentions = parseMentions(tweet);
        this.urls = parseURLS(tweet);
        this.tweet = tweet;
        this.timeStampString = timeStamp;
        if (timeStamp != null)
        {
            this.timeStampString = timeStamp;
        }
        this.user = user;
    }

    public List<String> getMentions() {
        return mentions;
    }

    public List<String> getUrls() {
        return urls;
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

    public void setMentions(List<String> mentions) {
        this.mentions = mentions;
    }

    public void setUrls(List<String> urls) {
        urls = urls;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTimeStampString() {
        return timeStampString;
    }

    public void setTimeStamp(String timeStampString) {
        this.timeStampString = timeStampString;
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
                "Mentions='" + mentions.toString() + '\'' +
                ", urls='" + urls.toString() + '\'' +
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
