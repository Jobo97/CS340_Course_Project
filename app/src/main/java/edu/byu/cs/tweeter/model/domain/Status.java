package edu.byu.cs.tweeter.model.domain;


import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Status {
    private List<String> Mentions;
    private List<String> Urls;
    private String tweet;
    private Timestamp timeStamp;            //Review this Timestamp class
    private User user;

    public Status(List<String> mentions, List<String> Urls, String tweet, Timestamp timeStamp, User user) {
        Mentions = mentions;
        this.Urls = Urls;
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


}
