package com.b5.findfurryfriends.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jinch on 4/28/2017.
 */

public interface FirebaseAuthWrapperInterface {
    void signIn();

    void signOut();

    boolean signInOnIntentResult(int requestCode, Intent data);

    void setActivity(AppCompatActivity activity);
}
