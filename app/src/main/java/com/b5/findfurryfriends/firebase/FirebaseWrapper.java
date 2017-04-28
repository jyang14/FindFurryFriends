package com.b5.findfurryfriends.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.b5.findfurryfriends.firebase.data.Animal;
import com.b5.findfurryfriends.firebase.data.User;
import com.b5.findfurryfriends.firebase.listeners.LoginListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by jinch on 4/12/2017.
 */

//TODO Refractor code create proper singleton move stuff to DataInterface and FirebaseAuthWrapper (make concrete)
public class FirebaseWrapper implements DataInterface, FirebaseAuthWrapperInterface // Java, hasAuthListener don't you support multiple inheritance?
{

    final static String TAG = "FirebaseAuth";
    protected FirebaseDatabase database;
    protected User user = null;
    private FirebaseAuthWrapper authWrapper;
    private AppCompatActivity activity;

    protected FirebaseWrapper(final AppCompatActivity activity) {
        authWrapper = new FirebaseAuthWrapper(activity);
        this.activity = activity;

        database = FirebaseDatabase.getInstance();

        authWrapper.mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    final DatabaseReference authRef = database.getReference("/users/auth");
                    authRef.addListenerForSingleValueEvent(new LoginListener(FirebaseWrapper.this, authRef, database));

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

    }

    public void setActivity(AppCompatActivity activity) {
        authWrapper.setActivity(activity);
        this.activity = activity;
    }

    public User getUser(ValueEventListener listener) {
        return user;
    }

    public void setUser(ValueEventListener listener, User user) {
        this.user = user;
    }

    @Override
    public List<Animal> fetch(int count) {
        return null;
    }

    @Override
    public void uploadAnimal(final Animal animal) {
        if (user == null || animal == null) {
            Log.w(TAG, "ERROR USER NOT INITIALIZED.");
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
                DatabaseReference newRef = database.getReference("animals/animals");
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


        final DatabaseReference myRef = database.getReference("animals/animals");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<List<Animal>> genericTypeIndicator = new GenericTypeIndicator<List<Animal>>() {
                };
                List<Animal> test = dataSnapshot.getValue(genericTypeIndicator);

                for (Animal temp : test) {
                    Log.v("Animal", temp.toString());
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public void signIn() {
        authWrapper.signIn();
    }

    @Override
    public void signOut() {
        authWrapper.signOut();
    }

    @Override
    public boolean signInOnIntentResult(int requestCode, Intent data) {
        return authWrapper.signInOnIntentResult(requestCode, data);
    }
}
