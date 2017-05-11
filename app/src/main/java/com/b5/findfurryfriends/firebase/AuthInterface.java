package com.b5.findfurryfriends.firebase;

import android.content.Context;
import android.content.Intent;

import com.b5.findfurryfriends.firebase.handlers.SignedInHandler;

/** AuthInterface.java
 *  Mass Academy Apps for Good - B5
 *  April 2017
 */
interface AuthInterface {
    void signIn();

    void signOut();

    void signInOnIntentResult(int requestCode, Intent data, SignedInHandler signedInHandler);

    void setContext(Context activity);
}
