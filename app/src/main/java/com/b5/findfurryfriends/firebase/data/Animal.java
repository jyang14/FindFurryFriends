package com.b5.findfurryfriends.firebase.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/** Animal.java
 *  Mass Academy Apps for Good - B5
 *  April 2017
 */
public class Animal implements Parcelable {

    public static final Parcelable.Creator<Animal> CREATOR = new Parcelable.Creator<Animal>() {
        @Override
        public Animal createFromParcel(Parcel in) {
            return new Animal(in);
        }

        @Override
        public Animal[] newArray(int size) {
            return new Animal[size];
        }
    };

    public String name;
    public String image;
    public String breed;
    public long userID;
    public long animalID;
    public int age;
    public String description;
    public String type;
    public List<String> tags;

    public Animal(String name, int age, String description, List<String> tags, String breed, String type) {
    /**
     *
     * @param name the pet's name
     * @param age the pet's age
     * @param description a brief description of the pet
     * @param tags
     * @param breed the pet's breed
     * @param type the type of animal
     */
    public Animal(String name, int age, String description, List<String> tags, String breed) {
        this.name = name;
        this.image = null;
        this.age = age;
        this.description = description;
        this.tags = tags;
        this.breed = breed;
        this.type = type;
    }

    public Animal() {

    }

    private Animal(Parcel in) {
        name = in.readString();
        image = in.readString();
        breed = in.readString();
        userID = in.readLong();
        animalID = in.readLong();
        age = in.readInt();
        description = in.readString();
        type = in.readString();
        if (in.readByte() == 0x01) {
            tags = new ArrayList<>();
            in.readStringList(tags);
        } else {
            tags = null;
        }
    }

    @Override
    public String toString() {
        return String.format("Animal{ name: \"%s\", breed: \"%s\", userID: %d, animalID: %d, age: %d, description: \"%s\"}", name, breed, userID, animalID, age, description,type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(breed);
        dest.writeLong(userID);
        dest.writeLong(animalID);
        dest.writeInt(age);
        dest.writeString(description);
        dest.writeString(type);
        if (tags == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeStringList(tags);
        }
    }
}