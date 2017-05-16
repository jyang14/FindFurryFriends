package com.b5.findfurryfriends.firebase.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public String userID;
    /**
     * The animal id.
     */
    public String animalID;
    /**
     * The age of animal.
     */
    public int age;
    /**
     * The description of animal.
     */
    public String description;
    /**
     * The type of animal
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
        userID = in.readString();
        animalID = in.readString();
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

    /**
     * Hashing function that returns string instead of int.
     * According to the birthday paradox I am safe as long there will be less than 2^64 entries
     *
     * @return Returns the SHA-256 of the class
     */
    public String hash() {

        String output;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(name.getBytes());
            digest.update((byte) 0);
            digest.update(userID.getBytes());
            digest.update((byte) 0);
            digest.update(image.getBytes());
            digest.update((byte) 0);
            digest.update(breed.getBytes());
            digest.update((byte) 0);
            digest.update(type.getBytes());
            digest.update((byte) 0);
            byte[] hash = digest.digest(description.getBytes());
            BigInteger bigInt = new BigInteger(1, hash);
            output = bigInt.toString(16);

            return output;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "N" + String.format("%X", System.nanoTime());
        }

    }

    @Override
    public String toString() {
        return String.format(Locale.US,
                "Animal{ name: \"%s\", breed: \"%s\", userID: %s, animalID: %s, age: %d, description: \"%s\", type: \"%s\"}",
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
        dest.writeString(userID);
        dest.writeString(animalID);
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