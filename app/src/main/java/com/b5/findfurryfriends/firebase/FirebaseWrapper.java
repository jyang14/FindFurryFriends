package com.b5.findfurryfriends.firebase;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.b5.findfurryfriends.firebase.data.Animal;
import com.b5.findfurryfriends.firebase.data.User;
import com.b5.findfurryfriends.firebase.handlers.FetcherHandler;
import com.b5.findfurryfriends.firebase.handlers.SignedInHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

//TODO Refactor code move stuff to DataWrapper and AuthWrapper (make concrete)
public class FirebaseWrapper implements AuthInterface, DataInterface, StorageInterface // Multiple inheritance hack
{

    final static String TAG = "FirebaseWrapper";

    //Lazy singleton
    private static FirebaseWrapper instance;

    private final AuthWrapper authWrapper;
    private final DataWrapper dataWrapper;
    private final StorageWrappper storageWrappper;

    private FirebaseWrapper(final Context activity) {
        Log.v(TAG, "Constructor called");
        authWrapper = new AuthWrapper(activity);
        dataWrapper = new DataWrapper();
        storageWrappper = new StorageWrappper(activity);

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

    /**
     * @param activity AppCompactActivity instances preferred
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

    @Override
    public void setContext(Context activity) {
        if (activity != null) {
            authWrapper.setContext(activity);
            storageWrappper.setContext(activity);
        }
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
    public void signInOnIntentResult(int requestCode, Intent data, SignedInHandler signedInHandler) {
        authWrapper.signInOnIntentResult(requestCode, data, signedInHandler);
    }

    @Override
    public User getUser() {
        return dataWrapper.getUser();
    }

    @Override
    public void setUser(User user) {
        dataWrapper.setUser(user);
    }

    @Override
    public void uploadAnimal(Animal animal) {
        dataWrapper.uploadAnimal(animal);
    }

    @Override
    public void search(List<String> tags, FetcherHandler handler) {
        dataWrapper.search(tags, handler);
    }

    @Override
    public void addFavorite(Animal animal) {
        dataWrapper.addFavorite(animal);
    }

    @Override
    public void getFavorites(FetcherHandler fetcherHandler) {
        dataWrapper.getFavorites(fetcherHandler);
    }

    @Override
    public void removeFavorite(Animal animal) {
        dataWrapper.removeFavorite(animal);
    }

    @Override
    public void createCaptureIntent(Animal animal) {
        storageWrappper.createCaptureIntent(animal);
    }

    @Override
    public void getImage(String name, ImageView imageView) {
        storageWrappper.getImage(name, imageView);
    }

    @Override
    public boolean uploadOnIntentResult(int requestCode, int resultCode, Intent data) {
        return storageWrappper.uploadOnIntentResult(requestCode, resultCode, data);
    }
}
