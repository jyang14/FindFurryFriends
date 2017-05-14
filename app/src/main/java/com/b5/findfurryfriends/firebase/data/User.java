package com.b5.findfurryfriends.firebase.data;

import java.util.ArrayList;
import java.util.List;

/**
 * User.java
 * Mass Academy Apps for Good - B5
 * April 2017
 * <p>
 * User data struct
 */
public class User {
    /**
     * The username.
     */
    public String username;
    /**
     * The user id.
     */
    public long userID;
    /**
     * The contact info.
     */
    public String contact;
    /**
     * The the list of ids of uploaded animals.
     */
    public List<Long> animalIDs;
    /**
     * The list of ids of the favorites animals.
     */
    public List<Long> favorites;

    /**
     * constructor: User
     *
     * @param username user's name
     * @param userID   generated from firebase
     * @param contact  user's email
     */
    public User(String username, long userID, String contact) {
        this.username = username;
        this.userID = userID;
        this.contact = contact;
        animalIDs = new ArrayList<>();
        favorites = new ArrayList<>();
    }

    /**
     * constructor: User
     * <p>
     * default constructor for Firebase. empty.
     */
    public User() {

    }
}
