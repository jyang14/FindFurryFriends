package com.b5.findfurryfriends.firebase.wrappers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.b5.findfurryfriends.firebase.data.Animal;
import com.b5.findfurryfriends.firebase.data.User;
import com.b5.findfurryfriends.firebase.handlers.FetchAnimalHandler;
import com.b5.findfurryfriends.firebase.handlers.FetchUserHandler;
import com.b5.findfurryfriends.firebase.handlers.SignedInHandler;
import com.b5.findfurryfriends.firebase.handlers.SignedOutHandler;

import java.util.List;

/**
 * FirebaseWrapper.java
 * Mass Academy Apps for Good - B5
 * April 2017
 * <p>
 * Redirects calls of functions to their respective implementations
 */
public class FirebaseWrapper implements AuthInterface, DataInterface, StorageInterface // Multiple inheritance hack
{

    /**
     * The constant TAG.
     */
    final static String TAG = "FirebaseWrapper";

    /**
     * The singleton instance.
     */
    private static FirebaseWrapper instance;

    private final AuthWrapper authWrapper;
    private final DataWrapper dataWrapper;
    private final StorageWrapper storageWrapper;

    /**
     * constructor: FirebaseWrapper
     *
     * @param context Context calling Firebase
     */
    private FirebaseWrapper(final Context context) {
        Log.v(TAG, "Constructor called");
        authWrapper = new AuthWrapper(context);
        dataWrapper = new DataWrapper();
        storageWrapper = new StorageWrapper(context);

        authWrapper.mAuthListener = new AuthListener(this, dataWrapper);

    }

    /**
     * method: getFirebase
     * <p>
     * Gets the FirebaseWrapper singleton instance.
     *
     * @param activity AppCompactActivity instances preferred
     * @return FirebaseWrapper instance
     */
    public static FirebaseWrapper getFirebase(@NonNull Context activity) {
        if (instance != null) {
            instance.setContext(activity);
        } else {
            instance = new FirebaseWrapper(activity);
        }
        return instance;

    }

    /**
     * method: setContext
     * <p>
     * Do not call
     *
     * @param activity Context calling Firebase
     */
    @Override
    public void setContext(Context activity) {
        if (activity != null) {
            authWrapper.setContext(activity);
            storageWrapper.setContext(activity);
        }
    }

    /**
     * method: signIn
     * <p>
     * Creates intent for sign in.
     */
    @Override
    public void signIn() {
        authWrapper.signIn();
    }

    /**
     * method: signOut
     * <p>
     * Sign out.
     *
     * @param signedOutHandler the signed out handler
     */
    @Override
    public void signOut(SignedOutHandler signedOutHandler) {
        authWrapper.signOut(signedOutHandler);
    }

    /**
     * method: signInOnIntentResult
     * <p>
     * Tries to sign in based on intent result
     *
     * @param requestCode     the request code in onIntentResult
     * @param data            intent data in onIntentResult
     * @param signedInHandler handler for signing in
     */
    @Override
    public void signInOnIntentResult(int requestCode, Intent data, SignedInHandler signedInHandler) {
        authWrapper.signInOnIntentResult(requestCode, data, signedInHandler);
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
        return dataWrapper.getUser();
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
        dataWrapper.setUser(user);
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
        dataWrapper.uploadAnimal(animal);
    }

    /**
     * method: search
     * <p>
     * Gets the list of all animals in the database
     *
     * @param tags    not used, please input null
     * @param handler handler of the resultant list of animals
     */
    @Override
    public void search(List<String> tags, FetchAnimalHandler handler) {
        dataWrapper.search(tags, handler);
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
        dataWrapper.addFavorite(animal);
    }

    /**
     * method: getFavorites
     * <p>
     * Gets list of favorite animals.
     *
     * @param fetchAnimalHandler handler of the list of favorite animals
     */
    @Override
    public void getFavorites(FetchAnimalHandler fetchAnimalHandler) {
        dataWrapper.getFavorites(fetchAnimalHandler);
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
        dataWrapper.removeFavorite(animal);
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
    public void getUserFromAnimal(Animal animal, FetchUserHandler userHandler) {
        dataWrapper.getUserFromAnimal(animal, userHandler);
    }

    /**
     * method: createCaptureIntent
     * <p>
     * Create image capture intent that will eventually upload {@code animal}.
     *
     * @param animal animal data to be uploaded
     */
    @Override
    public void createCaptureIntent(Animal animal) {
        storageWrapper.createCaptureIntent(animal);
    }

    /**
     * method: getImage
     * <p>
     * Gets the bitmap image by and puts it in the ImageView
     *
     * @param name      Name/hash of image
     * @param imageView ImageView to display the image in
     */
    @Override
    public void getImage(String name, ImageView imageView) {
        storageWrapper.getImage(name, imageView);
    }

    /**
     * method: uploadOnIntentResult
     *
     * @param requestCode the request code in onIntentResult
     * @param resultCode  the result code in onIntentResult
     * @param data        intent data in onIntentResult
     * @return true if upload is successful
     */
    @Override
    public boolean uploadOnIntentResult(int requestCode, int resultCode, Intent data) {
        return storageWrapper.uploadOnIntentResult(requestCode, resultCode, data);
    }

}
