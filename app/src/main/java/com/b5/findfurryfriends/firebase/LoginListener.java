package com.b5.findfurryfriends.firebase;

import android.util.Log;

import com.b5.findfurryfriends.firebase.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


//TODO Pretty up code
final class LoginListener implements ValueEventListener {

    private final DatabaseReference authRef;
    private final FirebaseDatabase database;
    private final FirebaseWrapper firebaseInterface;
    private final String TAG = "LOGIN";

    public LoginListener(FirebaseWrapper firebaseInterface, DatabaseReference authRef, FirebaseDatabase database) {
        this.firebaseInterface = firebaseInterface;
        this.authRef = authRef;
        this.database = database;
    }

    @Override
    public void onDataChange(final DataSnapshot authSnapshot) {
        Log.w(TAG, "Starting process");

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {

            String uid = mAuth.getCurrentUser().getUid();

            if (authSnapshot.hasChild(uid)) {

                long id = authSnapshot.child(uid).getValue(Long.class);
                final DatabaseReference userRef = database.getReference("/users/users/" + String.valueOf(id));

                userRef.addListenerForSingleValueEvent(new GetUserHandler());

            } else {

                final DatabaseReference idRef = database.getReference("users/last-id");
                idRef.addListenerForSingleValueEvent(new CreateUserHandler(mAuth, idRef));

            }
        } else {
            Log.w(TAG, "USER ID IS NULL");
        }
    }

    @Override
    public void onCancelled(DatabaseError error) {
        Log.w(TAG, "Failed to create get auth.", error.toException());

    }

    private class GetUserHandler implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            firebaseInterface.setUser(dataSnapshot.getValue(User.class));
        }

        @Override
        public void onCancelled(DatabaseError error) {
            Log.w(TAG, "Failed to get user.", error.toException());
        }
    }

    private class CreateUserHandler implements ValueEventListener {

        private final FirebaseAuth mAuth;
        private final DatabaseReference idRef;

        public CreateUserHandler(FirebaseAuth mAuth, DatabaseReference idRef) {
            this.mAuth = mAuth;
            this.idRef = idRef;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            long value = dataSnapshot.getValue(Long.class) + 1;
            idRef.setValue(value);
            Log.d(TAG, "Value is: " + value);

            FirebaseUser currentUser = mAuth.getCurrentUser();

            authRef.child(currentUser.getUid()).setValue(value);

            firebaseInterface.setUser(new User(currentUser.getDisplayName(), value, currentUser.getEmail()));
            DatabaseReference userRef = database.getReference("users/users/");
            userRef.child(String.valueOf(value)).setValue(firebaseInterface.getUser());
        }

        @Override
        public void onCancelled(DatabaseError error) {
            Log.w(TAG, "Failed to create user.", error.toException());
        }
    }

}
