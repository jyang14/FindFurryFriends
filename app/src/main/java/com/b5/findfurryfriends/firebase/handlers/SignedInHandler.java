package com.b5.findfurryfriends.firebase.handlers;

/**
 * SignedInHandler.java
 * Mass Academy Apps for Good - B5
 * May 2017
 * <p>
 * Used to provide a way the caller to specify behavior when sign in succeeds
 */
public interface SignedInHandler {
    /**
     * On sign in success.
     */
    void onSignInSuccess();
}
