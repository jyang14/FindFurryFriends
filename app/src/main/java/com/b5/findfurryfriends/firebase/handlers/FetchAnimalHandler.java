package com.b5.findfurryfriends.firebase.handlers;

import com.b5.findfurryfriends.firebase.data.Animal;

import java.util.List;

/** FetchAnimalHandler.java
 *  Mass Academy Apps for Good - B5
 *  April 2017
 */
public interface FetchAnimalHandler {

    void handle(List<Animal> results);

}
