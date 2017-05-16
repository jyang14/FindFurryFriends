package com.b5.findfurryfriends.firebase.wrappers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.b5.findfurryfriends.firebase.handlers.SignedInHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * AuthListener.java
 * Mass Academy Apps for Good - B5
 * April 2017
 * <p>
 * Listener of Firebase Authentication status
 */
class AuthListener implements FirebaseAuth.AuthStateListener {
    private final DataWrapper dataWrapper;
    private SignedInHandler signedInHandler;


    /**
     * constructor: AuthListener
     * <p>
     * Instantiates a new Auth listener.
     *
     * @param dataWrapper     the data wrapper
     */
    AuthListener(DataWrapper dataWrapper) {
        this.dataWrapper = dataWrapper;
    }

    /**
     * Gets the MD5 hash of the email for indexing
     * Assumes that one has an email and cannot change their email address for a given account
     *
     * @param email email address of the user
     * @return hash of the email
     */
    private String hashEmail(String email) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            byte[] hash = digest.digest(email.getBytes());
            BigInteger bigInt = new BigInteger(1, hash);
            return bigInt.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "X" + System.nanoTime();
        }

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

            DatabaseReference userRef = dataWrapper.usersRef.child(hashEmail(user.getEmail()));
            userRef.addListenerForSingleValueEvent(new LoginListener(dataWrapper, signedInHandler));

        } else {  // User is signed out
            Log.d(FirebaseWrapper.TAG, "onAuthStateChanged:signed_out");
        }
    }
}
