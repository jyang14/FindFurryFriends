package com.b5.findfurryfriends.firebase.wrappers;

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

/**
 * DataWrapper.java
 * Mass Academy Apps for Good - B5
 * April 2017
 * <p>
 * Implementation of Firebase Database functions
 */
@SuppressWarnings("ALL")
class DataWrapper implements DataInterface {

    static private final String TAG = "DATAWRAPPER";

    /**
     * The Database.
     */
    final FirebaseDatabase database;
    private User user = null;

    /**
     * Instantiates a new Data wrapper.
     */
    DataWrapper() {
        database = FirebaseDatabase.getInstance();
    }

    /**
     * method: getUser
     * <p>
     * Gets user.
     *
     * @return User user to be gotten
     */
    @Override
    public User getUser() {
        return user;
    }

    /**
     * method: setUser
     * <p>
     * Sets user.
     *
     * @param user the user
     */
    @Override
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * method: uploadAnimal
     * <p>
     * Upload animal.
     *
     * @param animal animal to be uploaded
     */
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
     * method: search
     * <p>
     * Gets the list of all animals in the database
     *
     * @param tags    TODO
     * @param handler handler of the resultant list of animals
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
                handler.handleAnimals(results);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read values.", error.toException());
            }
        });

    }

    /**
     * method: addFavorite
     * <p>
     * Add animal to favorites
     *
     * @param animal animal to be added to favorites
     */
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

    /**
     * method: updateUser
     * <p>
     * Updates the user on Firebase with the local cached user
     */
    private void updateUser() {
        if (user == null) {
            Log.w(FirebaseWrapper.TAG, "ERROR PARAMETERS NOT INITIALIZED.");
            return;
        }

        database.getReference("users/users/" + user.userID).setValue(user);
    }

    /**
     * method: removeFavorite
     * <p>
     * Remove animal from favorites.
     *
     * @param animal animal to remove from favorites
     */
    @Override
    public void getFavorites(final FetchAnimalHandler fetchAnimalHandler) {
        if (user == null || fetchAnimalHandler == null) {
            Log.w(FirebaseWrapper.TAG, "ERROR PARAMETERS NOT INITIALIZED.");
            return;
        }

        if (user.favorites == null)
            user.favorites = new ArrayList<>();

        final List<Animal> temp = new ArrayList<>();

        for (long id : user.favorites) {

            database.getReference("animals/animals/" + id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Animal animal = dataSnapshot.getValue(Animal.class);
                    temp.add(animal);

                    if (temp.size() == user.favorites.size()) {
                        temp.removeAll(Collections.singleton(null));
                        fetchAnimalHandler.handleAnimals(temp);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "ERROR: Animal not retrieved");
                }
            });

        }

    }

    /**
     * method: removeFavorite
     * <p>
     * Remove animal from favorites.
     *
     * @param animal animal to remove from favorites
     */
    @Override
    public void removeFavorite(Animal animal) {

        if (user == null || animal == null || user.favorites == null || !user.favorites.contains(animal.animalID)) {
            Log.w(FirebaseWrapper.TAG, "ERROR PARAMETERS NOT INITIALIZED.");
            return;
        }

        user.favorites.remove(animal.animalID);
        updateUser();

    }

    /**
     * method: getUserFromAnimal
     * <p>
     * Gets the user that uploaded the animal.
     *
     * @param animal      animal of the user to be fetched
     * @param userHandler handler of the fetched user data
     */
    @Override
    public void getUserFromAnimal(Animal animal, final FetchUserHandler userHandler) {
        if (animal == null || userHandler == null) {
            Log.w(FirebaseWrapper.TAG, "ERROR PARAMETERS NOT INITIALIZED.");
            return;
        }
        DatabaseReference userRef = database.getReference("users/users/" + animal.userID);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userHandler.handleUser(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v(TAG, "Cannot get user");
            }
        });
    }

    /**
     * Class for uploading animals
     */
    private class AnimalUploadListener implements ValueEventListener {

        private final DatabaseReference myRef;
        private final Animal animal;

        /**
         * Instantiates a new Animal upload listener.
         *
         * @param myRef  the my ref
         * @param animal the animal
         */
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
