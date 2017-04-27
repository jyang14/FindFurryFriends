package com.b5.findfurryfriends.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by sampendergast on 4/7/17.
 */

public class FirebaseAuthWrapper implements GoogleApiClient.ConnectionCallbacks {

    final static String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    GoogleApiClient mGoogleApiClient;
    AppCompatActivity activity;
    FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseAuth mAuth;
    private boolean hasAuthListener = true;
    private boolean signOut = false;

    FirebaseAuthWrapper(final AppCompatActivity activity) {
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the test2. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in test2 can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signIn() {
        if (hasAuthListener) {
            mAuth.addAuthStateListener(mAuthListener);
            hasAuthListener = false;
        }
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    public void signOut() {
        if (mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (mAuth != null) {
                                // Firebase sign out
                                mAuth.signOut();

                            }
                        }
                    });
        } else {
            mGoogleApiClient.connect();
            signOut = true;
        }

    }

    public boolean signInOnIntentResult(int requestCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.w(TAG, String.valueOf(result.isSuccess()));
            Log.v(TAG, result.getStatus().toString());
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                firebaseAuthWithGoogle(result.getSignInAccount());

                return true;
            }
        }
        return false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (signOut) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (mAuth != null) {
                                // Firebase sign out
                                mAuth.signOut();

                            }
                        }
                    });
            signOut = false;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //TODO implement function
    }
}

