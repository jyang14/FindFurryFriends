package com.b5.findfurryfriends.firebase.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Animal.java
 * Mass Academy Apps for Good - B5
 * April 2017
 * <p>
 * Animal data struct
 */
public class Animal implements Parcelable {

    /**
     * The constant Parcelable.Creator.
     */
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

    /**
     * The name.
     */
    public String name;
    /**
     * The image name stored on Firebase Storage.
     */
    public String image;
    /**
     * The breed of Animal.
     */
    public String breed;
    /**
     * The user id of uploader.
     */
    public long userID;
    /**
     * The animal id.
     */
    public long animalID;
    /**
     * The age of animal.
     */
    public int age;
    /**
     * The description of animal.
     */
    public String description;
    /**
     * The Type.
     */
    public String type;
    /**
     * The animal description tags.
     *
     * @deprecated
     */
    public List<String> tags;


    /**
     * constructor: Animal
     *
     * @param name        the pet's name
     * @param age         the pet's age
     * @param description a brief description of the pet
     * @param breed       the pet's breed
     * @param type        the type of animal
     */
    public Animal(String name, int age, String description, String breed, String type) {
        this.name = name;
        this.image = null;
        this.age = age;
        this.description = description;
        this.tags = null;
        this.breed = breed;
        this.type = type;
    }

    /**
     * constructor: Animal
     * <p>
     * default constructor for Firebase
     */
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
        return String.format(Locale.US,
                "Animal{ name: \"%s\", breed: \"%s\", userID: %d, animalID: %d, age: %d, description: \"%s\", type: \"%s\"}",
                name, breed, userID, animalID, age, description, type);
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