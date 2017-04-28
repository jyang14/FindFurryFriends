package com.b5.findfurryfriends.firebase;

import android.util.Log;

import com.b5.findfurryfriends.firebase.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

final class LoginListener implements ValueEventListener {
    private final DatabaseReference authRef;
    private final FirebaseDatabase database;
    private final String TAG = "LOGIN";
    private FirebaseWrapper firebaseInterface;


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
            if (authSnapshot.hasChild(mAuth.getCurrentUser().getUid())) {

                long id = authSnapshot.child(mAuth.getCurrentUser().getUid()).getValue(Long.class);

                final DatabaseReference userRef = database.getReference("/users/users/" + String.valueOf(id));

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        firebaseInterface.setUser(dataSnapshot.getValue(User.class));

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to get user.", error.toException());
                    }
                });

            } else {

                final DatabaseReference myRef = database.getReference("users/last-id");

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        long value = dataSnapshot.getValue(Long.class);
                        value++;
                        Log.d(TAG, "Value is: " + value);
                        firebaseInterface.setUser(new User(mAuth.getCurrentUser().getDisplayName(), value, mAuth.getCurrentUser().getEmail()));
                        myRef.setValue(value);
                        DatabaseReference newRef = database.getReference("users/users/");
                        newRef.child(String.valueOf(value)).setValue(firebaseInterface.getUser());
                        authRef.child(mAuth.getCurrentUser().getUid()).setValue(value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to create user.", error.toException());
                    }
                });
            }
        } else {
            Log.w(TAG, "USER ID IS NULL");
        }
    }

    @Override
    public void onCancelled(DatabaseError error) {
        Log.w(TAG, "Failed to create get auth.", error.toException());

    }
}
