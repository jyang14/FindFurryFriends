package com.b5.findfurryfriends.firebase.wrappers;

import com.b5.findfurryfriends.firebase.data.Animal;
import com.b5.findfurryfriends.firebase.data.User;
import com.b5.findfurryfriends.firebase.handlers.FetchAnimalHandler;
import com.b5.findfurryfriends.firebase.handlers.FetchUserHandler;

import java.util.List;

/**
 * DataInterface.java
 * Mass Academy Apps for Good - B5
 * April 2017
 */
interface DataInterface {
    User getUser();

    void setUser(User user);

    void uploadAnimal(Animal animal);

    void search(List<String> tags, FetchAnimalHandler handler);

    void addFavorite(Animal animal);

    void getFavorites(FetchAnimalHandler fetchAnimalHandler);

    void removeFavorite(Animal animal);

    void getUserFromAnimal(Animal animal, FetchUserHandler userHandler);

}
