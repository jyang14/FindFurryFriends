package com.b5.findfurryfriends.firebase;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/** FirebaseWrapper.java
 *  Mass Academy Apps for Good - B5
 *  April 2017
 */
//TODO Refactor code move stuff to DataWrapper and AuthWrapper (make concrete)
public class FirebaseWrapper implements AuthInterface, DataInterface, StorageInterface // Multiple inheritance hack
{

    final static String TAG = "FirebaseWrapper";

    private static FirebaseWrapper instance;

    private final AuthWrapper authWrapper;
    private final DataWrapper dataWrapper;
    private final StorageWrapper storageWrapper;

    /** constructor: FirebaseWrapper
     *
     * @param activity
     */
    private FirebaseWrapper(final Context activity) {
        Log.v(TAG, "Constructor called");
        authWrapper = new AuthWrapper(activity);
        dataWrapper = new DataWrapper();
        storageWrapper = new StorageWrapper(activity);

        authWrapper.mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) { // User is signed in

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    DatabaseReference authRef = dataWrapper.database.getReference("/users/auth");
                    authRef.addListenerForSingleValueEvent(new LoginListener(FirebaseWrapper.this, authRef, dataWrapper.database));

                } else {  // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

    }

    /** method: getFirebase
     *
     * @param activity AppCompactActivity instances preferred
     * @return FirebaseWrapper instance
     */
    public static FirebaseWrapper getFirebase(@NonNull Context activity) {
        assert (activity != null);
        if (instance != null) {
            instance.setContext(activity);
        } else {
            instance = new FirebaseWrapper(activity);
        }
        return instance;

    }

    /** method: setContext
     *
     * @param activity
     */
    @Override
    public void setContext(Context activity) {
        if (activity != null) {
            authWrapper.setContext(activity);
            storageWrapper.setContext(activity);
        }
    }

    /** method: signIn
     *
     */
    @Override
    public void signIn() {
        authWrapper.signIn();
    }

    /** method: signOut
     *
     */
    @Override
    public void signOut() {
        authWrapper.signOut();
    }

    /** method: signInOnIntentResult
     *
     * @param requestCode
     * @param data
     * @param signedInHandler
     */
    @Override
    public void signInOnIntentResult(int requestCode, Intent data, SignedInHandler signedInHandler) {
        authWrapper.signInOnIntentResult(requestCode, data, signedInHandler);
    }

    /** method: getUser
     *
     * @return User
     */
    @Override
    public User getUser() {
        return dataWrapper.getUser();
    }

    /** method: setUser
     *
     * @param user
     */
    @Override
    public void setUser(User user) {
        dataWrapper.setUser(user);
    }

    /** method: uploadAnimal
     *
     * @param animal
     */
    @Override
    public void uploadAnimal(Animal animal) {
        dataWrapper.uploadAnimal(animal);
    }

    /** method: search
     *
     * @param tags
     * @param handler
     */
    @Override
    public void search(List<String> tags, FetchAnimalHandler handler) {
        dataWrapper.search(tags, handler);
    }

    /** method: addFavorite
     *
     * @param animal
     */
    @Override
    public void addFavorite(Animal animal) {
        dataWrapper.addFavorite(animal);
    }

    /** method: getFavorites
     *
     * @param fetchAnimalHandler
     */
    @Override
    public void getFavorites(FetchAnimalHandler fetchAnimalHandler) {
        dataWrapper.getFavorites(fetchAnimalHandler);
    }

    /** method: removeFavorite
     *
     * @param animal
     */
    @Override
    public void removeFavorite(Animal animal) {
        dataWrapper.removeFavorite(animal);
    }

    /** method: getUserFromAnimal
     *
     * @param animal
     * @param userHandler
     */
    @Override
    public void getUserFromAnimal(Animal animal, FetchUserHandler userHandler) {
        dataWrapper.getUserFromAnimal(animal, userHandler);
    }

    /** method: createCaptureIntent
     *
     * @param animal
     */
    @Override
    public void createCaptureIntent(Animal animal) {
        storageWrapper.createCaptureIntent(animal);
    }

    /** method: getImage
     *
     * @param name
     * @param imageView
     */
    @Override
    public void getImage(String name, ImageView imageView) {
        storageWrapper.getImage(name, imageView);
    }

    /** method: uploadOnIntentResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     */
    @Override
    public boolean uploadOnIntentResult(int requestCode, int resultCode, Intent data) {
        return storageWrapper.uploadOnIntentResult(requestCode, resultCode, data);
    }
}
