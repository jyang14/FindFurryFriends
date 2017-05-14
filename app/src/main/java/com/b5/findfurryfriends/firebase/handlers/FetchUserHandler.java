package com.b5.findfurryfriends.firebase.handlers;

import com.b5.findfurryfriends.firebase.data.User;

/**
 * FetchUserHandler.java
 * Mass Academy Apps for Good - B5
 * May 2017
 * <p>
 * Used to provide a way for the user returned by the FirebaseWrapper to be handled
 */
public interface FetchUserHandler {

    /**
     * Handles user fetched by the FirebaseWrapper.
     *
     * @param user the user
     */
    void handleUser(User user);

}
