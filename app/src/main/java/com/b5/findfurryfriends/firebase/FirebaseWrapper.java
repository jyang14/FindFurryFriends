package com.b5.findfurryfriends.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by sampendergast on 4/7/17.
 */

public class FirebaseWrapper extends FirebaseInterface {

    boolean signOut = false;

    FirebaseWrapper(final AppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void signIn() {
        if (why) {
            mAuth.addAuthStateListener(mAuthListener);
            why = false;
        }
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void signOut() {

        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
            signOut = true;
        } else {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (mAuth != null && mAuth.getCurrentUser() != null) {
                                // Firebase sign out
                                mAuth.signOut();

                            }
                        }
                    });
        }

    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.w(TAG, String.valueOf(result.isSuccess()));
            Log.v(TAG, result.getStatus().toString());
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

                final DatabaseReference authRef = database.getReference("/users/auth");

                authRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot authSnapshot) {
                        Log.w(TAG, "Test");
                        if (mAuth.getCurrentUser() != null) {
                            if (authSnapshot.hasChild(mAuth.getCurrentUser().getUid())) {

                                long id = authSnapshot.child(mAuth.getCurrentUser().getUid()).getValue(Long.class);

                                final DatabaseReference userRef = database.getReference("/users/" + String.valueOf(id));

                                userRef.addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        user = dataSnapshot.getValue(User.class);

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
                                        user = new User(mAuth.getCurrentUser().getDisplayName(), value, mAuth.getCurrentUser().getEmail());
                                        myRef.setValue(value);
                                        DatabaseReference newRef = database.getReference("users");
                                        newRef.child(String.valueOf(value)).setValue(user);
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
                });

                return true;

            }
        }
        return false;
    }

    @Override
    public List<Animal> fetch(int count) {
        return null;
    }

    @Override
    public void uploadAnimal(final Animal animal) {
        if (user == null || animal == null) {
            Log.w(TAG, "ERROR USER NOT INITIALIZED.", null);
            return;
        }

        animal.userID = user.userID;

        final DatabaseReference myRef = database.getReference("animals/last-id");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                long value = dataSnapshot.getValue(Long.class);
                value++;
                Log.d(TAG, "Value is: " + value);
                myRef.setValue(value);
                DatabaseReference newRef = database.getReference("animals");
                animal.animalID = value;
                newRef.child(String.valueOf(value)).setValue(animal);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void search(List<String> tags, FetcherHandler handler) {
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (signOut) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (mAuth != null && mAuth.getCurrentUser() != null) {
                                // Firebase sign out
                                mAuth.signOut();

                            }
                        }
                    });
            signOut = false;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
