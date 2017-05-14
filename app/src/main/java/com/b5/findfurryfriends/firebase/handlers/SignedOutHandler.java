package com.b5.findfurryfriends.firebase.handlers;

/**
 * SignedOutHandler.java
 * Mass Academy Apps for Good - B5
 * April 2017
 * <p>
 * Used to provide a way the caller to specify behavior when sign out succeeds
 */
public interface SignedOutHandler {

    /**
     * On sign out.
     */
    void onSignOut();
}
