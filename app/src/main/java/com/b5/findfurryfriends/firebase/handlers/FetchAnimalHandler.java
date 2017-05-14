package com.b5.findfurryfriends.firebase.handlers;

import com.b5.findfurryfriends.firebase.data.Animal;

import java.util.List;

/**
 * FetchAnimalHandler.java
 * Mass Academy Apps for Good - B5
 * April 2017
 * <p>
 * Used to provide a way for the animals returned by the FirebaseWrapper to be handled
 */
public interface FetchAnimalHandler {

    /**
     * Handle the animals returned by the FirebaseWrapper
     *
     * @param results the resultant animals
     */
    void handleAnimals(List<Animal> results);

}
