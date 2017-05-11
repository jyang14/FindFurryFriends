package com.b5.findfurryfriends.firebase.data;

import java.util.ArrayList;
import java.util.List;

/** User.java
 *  Mass Academy Apps for Good - B5
 *  April 2017
 */
public class User {
    public String username;
    public long userID;
    public String contact;
    public List<Long> animalIDs;
    public List<Long> favorites;

    /** constructor: User
     *
     * @param username user's name
     * @param userID generated from firebase
     * @param contact user's email
     */
    public User(String username, long userID, String contact) {
        this.username = username;
        this.userID = userID;
        this.contact = contact;
        animalIDs = new ArrayList<>();
        favorites = new ArrayList<>();
    }

    public User(){

    }
}
