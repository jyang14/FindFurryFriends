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

import java.util.ArrayList;
import java.util.List;

class DataWrapper implements DataInterface {

    final FirebaseDatabase database;
    private User user = null;

    public DataWrapper() {
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
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

    @Override
    public void search(List<String> tags, final FetcherHandler handler) {

        final DatabaseReference myRef = database.getReference("animals/animals");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<List<Animal>> genericTypeIndicator = new GenericTypeIndicator<List<Animal>>() {
                };
                List<Animal> results = dataSnapshot.getValue(genericTypeIndicator);
                if(results  == null)
                    results = new ArrayList<>();

                handler.handle(results);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(FirebaseWrapper.TAG, "Failed to read value.", error.toException());
            }
        });

    }
}
