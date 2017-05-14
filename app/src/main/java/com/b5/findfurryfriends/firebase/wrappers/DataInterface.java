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
 * <p>
 * Interface of Firebase Database functions
 */
interface DataInterface {

    /**
     * method: getUser
     * <p>
     * Gets user.
     *
     * @return User user to be gotten
     */
    User getUser();

    /**
     * method: setUser
     * <p>
     * Sets user.
     *
     * @param user the user
     */
    void setUser(User user);

    /**
     * Upload animal.
     *
     * @param animal the animal
     */
    void uploadAnimal(Animal animal);

    /**
     * method: search
     * <p>
     * Gets the list of all animals in the database
     *
     * @param tags    not used, please input null
     * @param handler handler of the resultant list of animals
     */
    void search(List<String> tags, FetchAnimalHandler handler);

    /**
     * method: addFavorite
     * <p>
     * Add animal to favorites
     *
     * @param animal animal to be added to favorites
     */
    void addFavorite(Animal animal);

    /**
     * method: getFavorites
     * <p>
     * Gets list of favorite animals.
     *
     * @param fetchAnimalHandler handler of the list of favorite animals
     */
    void getFavorites(FetchAnimalHandler fetchAnimalHandler);

    /**
     * method: removeFavorite
     * <p>
     * Remove animal from favorites.
     *
     * @param animal animal to remove from favorites
     */
    void removeFavorite(Animal animal);

    /**
     * method: getUserFromAnimal
     * <p>
     * Gets the user that uploaded the animal.
     *
     * @param animal      animal of the user to be fetched
     * @param userHandler handler of the fetched user data
     */
    void getUserFromAnimal(Animal animal, FetchUserHandler userHandler);

}
