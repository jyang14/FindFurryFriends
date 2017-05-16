package com.b5.findfurryfriends.firebase.data;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
     * The contact info.
     */
    public String contact;
    /**
     * The the list of ids of uploaded animals.
     */
    public List<String> animalIDs;
    /**
     * The list of ids of the favorites animals.
     */
    public List<String> favorites;

    /**
     * constructor: User
     *
     * @param username user's name
     * @param contact  user's email
     */
    public User(String username, String contact) {
        this.username = username;
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

    /**
     * Gets the MD5 hash of the email for indexing
     * Assumes that one has an email and cannot change their email address for a given account
     *
     * @return hash of the email
     */
    public String hashEmail() {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(contact.getBytes());
            BigInteger bigInt = new BigInteger(1, hash);
            return bigInt.toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "X" + System.nanoTime();
        }
    }
}
