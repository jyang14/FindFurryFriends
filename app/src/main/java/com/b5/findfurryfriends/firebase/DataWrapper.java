package com.b5.findfurryfriends.firebase;

import android.util.Log;

import com.b5.findfurryfriends.firebase.data.Animal;
import com.b5.findfurryfriends.firebase.data.User;
import com.b5.findfurryfriends.firebase.handlers.FetchAnimalHandler;
import com.b5.findfurryfriends.firebase.handlers.FetchUserHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class DataWrapper implements DataInterface {

    static private final String TAG = "DATAWRAPPER";

    final FirebaseDatabase database;
    private User user = null;

    private List<Animal> temp;

    DataWrapper() {
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
            Log.w(TAG, "ERROR PARAMETERS NOT INITIALIZED.");
            return;
        }

        animal.userID = user.userID;

        final DatabaseReference idRef = database.getReference("animals/last-id");

        idRef.addListenerForSingleValueEvent(new AnimalUploadListener(idRef, animal));
    }


    /**
     * @param tags    Set as null
     * @param handler The handler of the results
     * @// TODO: 5/10/2017 Implement tags
     */
    @Override
    public void search(List<String> tags, final FetchAnimalHandler handler) {

        if (handler == null) {
            Log.w(TAG, "ERROR, SEARCH CALLED WITHOUT HANDLER");
            return;
        }
        
        final DatabaseReference myRef = database.getReference("animals/animals");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<List<Animal>> genericTypeIndicator = new GenericTypeIndicator<List<Animal>>() {
                };
                List<Animal> results = dataSnapshot.getValue(genericTypeIndicator);
                if (results == null)
                    results = new ArrayList<>();

                results.removeAll(Collections.singleton(null));
                handler.handle(results);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read values.", error.toException());
            }
        });

    }

    @Override
    public void addFavorite(Animal animal) {
        if (user == null || animal == null) {
            Log.w(TAG, "ERROR PARAMETERS NOT INITIALIZED.");
            return;
        }

        // Checks if null
        if (user.favorites == null)
            user.favorites = new ArrayList<>();

        // Adds to favorite
        user.favorites.add(animal.animalID);

        // Updates user on database
        updateUser();
    }

    private void updateUser() {
        if (user == null) {
            Log.w(FirebaseWrapper.TAG, "ERROR PARAMETERS NOT INITIALIZED.");
            return;
        }

        database.getReference("users/users/" + user.userID).setValue(user);
    }

    @Override
    public void getFavorites(final FetchAnimalHandler fetchAnimalHandler) {
        if (user == null || fetchAnimalHandler == null) {
            Log.w(FirebaseWrapper.TAG, "ERROR PARAMETERS NOT INITIALIZED.");
            return;
        }

        if (user.favorites == null)
            user.favorites = new ArrayList<>();

        temp = new ArrayList<>();

        for (long id : user.favorites) {

            database.getReference("animals/animals/" + id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Animal animal = dataSnapshot.getValue(Animal.class);
                    temp.add(animal);

                    if (temp.size() == user.favorites.size()) {
                        fetchAnimalHandler.handle(temp);
                        temp = null; // Why not?
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.v(TAG, "ERROR: Animal not retrieved");
                }
            });

        }

    }

    @Override
    public void removeFavorite(Animal animal) {

        if (user == null || animal == null) {
            Log.w(FirebaseWrapper.TAG, "ERROR PARAMETERS NOT INITIALIZED.");
            return;
        }

        user.favorites.remove(animal.animalID);
        updateUser();

    }

    @Override
    public void getUserFromAnimal(Animal animal, final FetchUserHandler userHandler) {
        DatabaseReference userRef = database.getReference("users/users/" + animal.userID);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userHandler.handleUser(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v(TAG, "Cannot get user email");
            }
        });
    }

    private class AnimalUploadListener implements ValueEventListener {

        private final DatabaseReference myRef;
        private final Animal animal;

        public AnimalUploadListener(DatabaseReference myRef, Animal animal) {
            this.myRef = myRef;
            this.animal = animal;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            long value = dataSnapshot.getValue(Long.class) + 1;
            myRef.setValue(value);
            animal.animalID = value;
            Log.d(FirebaseWrapper.TAG, "Value is: " + value);

            if (user.animalIDs == null)
                user.animalIDs = new ArrayList<>();

            user.animalIDs.add(value);
            updateUser();

            DatabaseReference animalRef = database.getReference("animals/animals");
            animalRef.child(String.valueOf(value)).setValue(animal);

        }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Log.w(FirebaseWrapper.TAG, "Failed to read value.", error.toException());
        }

    }

}
