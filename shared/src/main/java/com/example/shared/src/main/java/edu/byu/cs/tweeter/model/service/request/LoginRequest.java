package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request;

import java.util.Arrays;

/**
 * Contains all the information needed to make a login request.
 */
public class LoginRequest {

    private String username;
    private String password;

    private String firstname;
    private String lastname;

    private byte [] imageBytes;
    private String imageEncoded;
    private boolean registered;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }




    public LoginRequest() {
    }

    /**
     * Creates an instance.
     *
     * @param username the username of the user to be logged in.
     * @param password the password of the user to be logged in.
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
        registered = false;

        firstname = null;
        lastname = null;
        imageBytes = null;
    }

    public LoginRequest(String username, String password, String firstname, String lastname, byte [] imageBytes) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.imageBytes = imageBytes;
        registered = true;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", imageBytes=" + Arrays.toString(imageBytes) +
                ", imageEncoded='" + imageEncoded + '\'' +
                ", registered=" + registered +
                '}';
    }

    /**
     * Returns the username of the user to be logged in by this request.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user to be logged in by this request.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public boolean getRegistered() {
        return registered;
    }

    public String getImageEncoded() {
        return imageEncoded;
    }

    public void setImageEncoded(String imageEncoded) {
        this.imageEncoded = imageEncoded;
    }
}
