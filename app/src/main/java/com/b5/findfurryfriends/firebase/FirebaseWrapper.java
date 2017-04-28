package com.b5.findfurryfriends.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by jinch on 4/12/2017.
 */

//TODO Refractor code create proper singleton move stuff to DataInterface and AuthWrapper (make concrete)
public class FirebaseWrapper extends DataInterface implements FirebaseAuthWrapperInterface // Java, hasAuthListener don't you support multiple inheritance?
{

    final static String TAG = "FirebaseAuth";
    private AuthWrapper authWrapper;

    protected FirebaseWrapper(final AppCompatActivity activity) {
        super();
        authWrapper = new AuthWrapper(activity);

        authWrapper.mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != fetch(0)) {
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
}
