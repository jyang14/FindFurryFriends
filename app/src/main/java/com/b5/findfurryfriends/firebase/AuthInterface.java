package com.b5.findfurryfriends.firebase;

import android.content.Context;
import android.content.Intent;

import com.b5.findfurryfriends.firebase.handlers.SignedInHandler;

interface AuthInterface {
    void signIn();

    void signOut();

    void signInOnIntentResult(int requestCode, Intent data, SignedInHandler signedInHandler);

    void setContext(Context activity);
}
