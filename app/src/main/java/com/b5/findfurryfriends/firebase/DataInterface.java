package com.b5.findfurryfriends.firebase;

import android.util.Log;

import com.b5.findfurryfriends.firebase.data.Animal;
import com.b5.findfurryfriends.firebase.data.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by sampendergast on 4/7/17.
 */

public abstract class DataInterface {

    protected FirebaseDatabase database;
    protected User user = null;

    public DataInterface() {
        database = FirebaseDatabase.getInstance();
    }

    public User getUser(ValueEventListener listener) {
        return user;
    }

    public void setUser(ValueEventListener listener, User user) {
        this.user = user;
    }

    public List<Animal> fetch(int count) {
        return null;
    }


    public void uploadAnimal(final Animal animal) {
        if (user == null || animal == null) {
            Log.w(FirebaseWrapper.TAG, "ERROR USER NOT INITIALIZED.");
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
                Log.d(FirebaseWrapper.TAG, "Value is: " + value);
                myRef.setValue(value);
                DatabaseReference newRef = database.getReference("animals/animals");
                animal.animalID = value;
                newRef.child(String.valueOf(value)).setValue(animal);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(FirebaseWrapper.TAG, "Failed to read value.", error.toException());
            }
        });
    }

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
                Log.w(FirebaseWrapper.TAG, "Failed to read value.", error.toException());
            }
        });

    }
}
