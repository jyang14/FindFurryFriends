package com.b5.findfurryfriends.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

interface AuthInterface {
    void signIn();

    void signOut();

    boolean signInOnIntentResult(int requestCode, Intent data);

    void setActivity(AppCompatActivity activity);
}
