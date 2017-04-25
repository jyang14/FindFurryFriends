package com.b5.findfurryfriends.firebase;

import java.util.List;

/**
 * Created by sampendergast on 3/28/17.
 */

public class Animal {
    public String name;
    public String image;
    public long userID;
    public long animalID;
    public short age;
    public String description;
    public List<String> tags;

    public Animal(String name, String image, short age, String description, List<String> tags) {
        this.name = name;
        this.image = image;
        this.age = age;
        this.description = description;
        this.tags = tags;
    }

    public Animal(){

    }

}
