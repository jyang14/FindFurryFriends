package com.b5.findfurryfriends.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.b5.findfurryfriends.firebase.data.Animal;
import com.b5.findfurryfriends.firebase.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

//TODO Refactor code move stuff to DataWrapper and AuthWrapper (make concrete)
public class FirebaseWrapper implements AuthInterface, DataInterface // Java, hasAuthListener don't you support multiple inheritance?
{

    final static String TAG = "FirebaseAuth";
    //Lazy singleton
    private static FirebaseWrapper instance;
    private final AuthWrapper authWrapper;
    private final DataWrapper dataWrapper;

    private FirebaseWrapper(final AppCompatActivity activity) {
        authWrapper = new AuthWrapper(activity);
        dataWrapper = new DataWrapper();

        authWrapper.mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != fetch(0)) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    final DatabaseReference authRef = dataWrapper.database.getReference("/users/auth");
                    authRef.addListenerForSingleValueEvent(new LoginListener(FirebaseWrapper.this, authRef, dataWrapper.database));

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

    }

    public static FirebaseWrapper getFirebase(AppCompatActivity activity) {
        if (instance != null) {
            instance.setActivity(activity);
        } else {
            instance = new FirebaseWrapper(activity);
        }
        return instance;

    }

    @Override
    public void setActivity(AppCompatActivity activity) {
        authWrapper.setActivity(activity);
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

    @Override
    public User getUser() {
        return dataWrapper.getUser();
    }

    @Override
    public void setUser(User user) {
        dataWrapper.setUser(user);
    }

    @Override
    public List<Animal> fetch(int count) {
        return dataWrapper.fetch(count);
    }

    @Override
    public void uploadAnimal(Animal animal) {
        dataWrapper.uploadAnimal(animal);
    }

    @Override
    public void search(List<String> tags, FetcherHandler handler) {
        dataWrapper.search(tags, handler);
    }
}
