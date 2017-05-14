package com.b5.findfurryfriends.firebase.wrappers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.b5.findfurryfriends.firebase.handlers.SignedInHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * AuthListener.java
 * Mass Academy Apps for Good - B5
 * April 2017
 * <p>
 * Listener of Firebase Authentication status
 */
class AuthListener implements FirebaseAuth.AuthStateListener {
    private final FirebaseWrapper firebaseWrapper;
    private final DataWrapper dataWrapper;
    private SignedInHandler signedInHandler;

    /**
     * constructor: AuthListener
     * <p>
     * Instantiates a new Auth listener.
     *
     * @param firebaseWrapper the firebase wrapper
     * @param dataWrapper     the data wrapper
     */
    AuthListener(FirebaseWrapper firebaseWrapper, DataWrapper dataWrapper) {
        this.firebaseWrapper = firebaseWrapper;
        this.dataWrapper = dataWrapper;
    }

    /**
     * method: setSignedInHandler
     * <p>
     * Sets signed in handler.
     *
     * @param signedInHandler the signed in handler
     */
    void setSignedInHandler(SignedInHandler signedInHandler) {
        this.signedInHandler = signedInHandler;
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) { // User is signed in

            Log.d(FirebaseWrapper.TAG, "onAuthStateChanged:signed_in:" + user.getUid());

            DatabaseReference authRef = dataWrapper.database.getReference("/users/auth");
            authRef.addListenerForSingleValueEvent(new LoginListener(firebaseWrapper, authRef, dataWrapper.database, signedInHandler));

        } else {  // User is signed out
            Log.d(FirebaseWrapper.TAG, "onAuthStateChanged:signed_out");
        }
    }
}
