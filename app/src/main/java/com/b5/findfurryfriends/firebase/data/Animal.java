package com.b5.findfurryfriends.firebase.data;

import java.util.List;

public class Animal {
    public String name;
    public String image;
    public String breed;
    public long userID;
    public long animalID;
    public int age;
    public String description;
    public List<String> tags;

    public Animal(String name, String image, int age, String description, List<String> tags, String breed) {
        this.name = name;
        this.image = image;
        this.age = age;
        this.description = description;
        this.tags = tags;
        this.breed = breed;
    }

    public Animal(){

    }

    @Override
    public String toString(){
        return String.format("Animal{ name: \"%s\", userID: %d, animalID: %d, age: %d, description: \"%s\"}",name,userID,animalID, age, description);
    }

}
