package com.b5.findfurryfriends.firebase.wrappers;

import android.content.Context;
import android.content.Intent;

import com.b5.findfurryfriends.firebase.handlers.SignedInHandler;
import com.b5.findfurryfriends.firebase.handlers.SignedOutHandler;

/**
 * AuthInterface.java
 * Mass Academy Apps for Good - B5
 * April 2017
 * <p>
 * Interface of Firebase Authentication functions
 */
interface AuthInterface {

    /**
     * method: signIn
     * <p>
     * Creates intent for sign in.
     */
    void signIn();

    /**
     * method: signOut
     * <p>
     * Sign out.
     *
     * @param signedOutHandler the signed out handler
     */
    void signOut(SignedOutHandler signedOutHandler);

    /**
     * method: signInOnIntentResult
     * <p>
     * Tries to sign in based on intent result
     *
     * @param requestCode     the request code in onIntentResult
     * @param data            intent data in onIntentResult
     * @param signedInHandler handler for signing in
     */
    void signInOnIntentResult(int requestCode, Intent data, SignedInHandler signedInHandler);

    /**
     * method: setContext
     * <p>
     * Do not call publicly
     *
     * @param activity Context calling Firebase
     */
    void setContext(Context activity);
}
