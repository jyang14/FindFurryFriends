package com.b5.findfurryfriends.firebase.data;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String username;
    public long userID;
    public String contact;
    public List<Long> animalIDs;
    public List<Long> favorites;

    public User(String username, long userID, String contact) {
        this.username = username;
        this.userID = userID;
        this.contact = contact;
        animalIDs = new ArrayList<>();
        favorites = new ArrayList<>();
    }

    public User(){
        //Cringe
    }
}
