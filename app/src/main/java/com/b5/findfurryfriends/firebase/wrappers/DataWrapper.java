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
import java.util.HashMap;
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
    /**
     * The animal reference
     */
    final DatabaseReference animalRef;
    /**
     * The user reference
     */
    final DatabaseReference usersRef;


    private User user = null;

    /**
     * Instantiates a new Data wrapper.
     */
    DataWrapper() {
        database = FirebaseDatabase.getInstance();
        animalRef = database.getReference("animals");
        usersRef = database.getReference("users");
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
    public void uploadAnimal(Animal animal) {

        if (user == null || animal == null || animal.image == null || animal.hash() == null) {
            Log.w(TAG, "ERROR PARAMETERS NOT INITIALIZED.");
            return;
        }

        animal.userID = user.hashEmail();
        animal.animalID = animal.hash();

        animalRef.child(animal.hash()).setValue(animal);

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

        animalRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<HashMap<String, Animal>> genericTypeIndicator = new GenericTypeIndicator<HashMap<String, Animal>>() {
                };
                HashMap<String, Animal> results = dataSnapshot.getValue(genericTypeIndicator);
                if (results == null)
                    results = new HashMap<>();

                List<Animal> list = new ArrayList<>(results.values());
                list.removeAll(Collections.singleton(null));
                handler.handleAnimals(list);


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
        if (user == null || animal == null || animal.hash() == null) {
            Log.w(TAG, "ERROR PARAMETERS NOT INITIALIZED.");
            return;
        }

        // Checks if null
        if (user.favorites == null)
            user.favorites = new ArrayList<>();

        // Adds to favorite
        user.favorites.add(animal.hash());

        // Updates user on database
        updateUser();
    }

    /**
     * method: updateUser
     * <p>
     * Updates the user on Firebase with the local cached user
     */
    void updateUser() {
        if (user == null || user.hashEmail() == null) {
            Log.w(FirebaseWrapper.TAG, "ERROR PARAMETERS NOT INITIALIZED.");
            return;
        }

        usersRef.child(user.hashEmail()).setValue(user);
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

        for (String id : user.favorites) {

            animalRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
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

        if (user == null || user.hashEmail() == null || animal == null || user.favorites == null || animal.hash() == null || !user.favorites.contains(animal.hash())) {
            Log.w(FirebaseWrapper.TAG, "ERROR PARAMETERS NOT INITIALIZED.");
            return;
        }

        user.favorites.remove(animal.hash());
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
        if (animal == null || animal.hash() == null || userHandler == null) {
            Log.w(FirebaseWrapper.TAG, "ERROR PARAMETERS NOT INITIALIZED.");
            return;
        }
        usersRef.child(animal.userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null)
                    userHandler.handleUser(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v(TAG, "Cannot get user");
            }
        });
    }

}
