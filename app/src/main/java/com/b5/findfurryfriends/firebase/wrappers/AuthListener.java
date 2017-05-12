package com.b5.findfurryfriends.firebase.wrappers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.b5.findfurryfriends.firebase.handlers.SignedInHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by jinch on 5/11/2017.
 */
class AuthListener implements FirebaseAuth.AuthStateListener {
    private FirebaseWrapper firebaseWrapper;
    private DataWrapper dataWrapper;
    private SignedInHandler signedInHandler;

    public AuthListener(FirebaseWrapper firebaseWrapper, DataWrapper dataWrapper) {
        this.firebaseWrapper = firebaseWrapper;
        this.dataWrapper = dataWrapper;
    }

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
