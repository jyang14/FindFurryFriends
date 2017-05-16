package com.b5.findfurryfriends.firebase.wrappers;

import android.util.Log;

import com.b5.findfurryfriends.firebase.data.User;
import com.b5.findfurryfriends.firebase.handlers.SignedInHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * LoginListener.java
 * Mass Academy Apps for Good - B5
 * April 2017
 * <p>
 * Class for logging in
 */
final class LoginListener implements ValueEventListener {

    private final DataWrapper database;
    private final SignedInHandler signedInHandler;
    private final String TAG = "LOGIN";

    /**
     * Instantiates a new Login listener.
     *
     * @param database        the database
     * @param signedInHandler the signed in handler
     */
    public LoginListener(DataWrapper database, SignedInHandler signedInHandler) {
        this.database = database;
        this.signedInHandler = signedInHandler;
    }

    @Override
    public void onDataChange(final DataSnapshot authSnapshot) {
        Log.w(TAG, "Starting process");

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {

            User user = authSnapshot.getValue(User.class);

            if (user == null) {
                user = new User(firebaseUser.getDisplayName(), firebaseUser.getEmail());
                database.setUser(user);
                database.updateUser();
            } else {
                database.setUser(user);
            }
            if (signedInHandler != null)
                signedInHandler.onSignInSuccess();

        } else {
            Log.w(TAG, "USER ID IS NULL");
        }
    }

    @Override
    public void onCancelled(DatabaseError error) {
        Log.w(TAG, "Failed to create get auth.", error.toException());

    }
}
