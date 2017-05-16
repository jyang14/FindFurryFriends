package com.b5.findfurryfriends.firebase.wrappers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.b5.findfurryfriends.firebase.handlers.SignedInHandler;
import com.b5.findfurryfriends.firebase.handlers.SignedOutHandler;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
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
 * AuthWrapper.java
 * Mass Academy Apps for Good - B5
 * April 2017
 * <p>
 * Implementation of Firebase Authentication functions
 */
class AuthWrapper implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, AuthInterface {

    private final String TAG = "FirebaseAuth";
    private final int RC_SIGN_IN = 9001;
    private final GoogleApiClient mGoogleApiClient;
    private final FirebaseAuth mAuth;
    private final AuthListener mAuthListener;

    /**
     * The Authentication State Listener .
     */
    private Context activity;
    private boolean signOut = false;
    private SignedOutHandler signedOutHandler;

    /**
     * constructor: AuthWrapper
     * <p>
     * Instantiates a new AuthWrapper.
     *
     * @param activity     the activity
     * @param authListener the authentication state listener
     */
    AuthWrapper(final Context activity, AuthListener authListener) {
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = authListener;
        mAuth.addAuthStateListener(authListener);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("1055526604988-3ag104h10gjtt0btuvl9nsjehqdfv3no.apps.googleusercontent.com").requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(activity).enableAutoManage(((AppCompatActivity) activity) /* FragmentActivity */, this /* OnConnectionFailedListener */).addApi(com.google.android.gms.auth.api.Auth.GOOGLE_SIGN_IN_API, gso).build();
        mGoogleApiClient.connect();

    }

    /**
     * method: setContext
     * <p>
     * Do not call
     *
     * @param activity Context calling Firebase
     * @deprecated
     */
    public void setContext(Context activity) {
        this.activity = activity;
    }

    /**
     * method: firebaseAuthWithGoogle
     * <p>
     * Authenticates Firebase with google account
     *
     * @param acct authenticated google account
     */
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((AppCompatActivity) activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the test2. If sign in succeeds
                        // the auth state listener will be notified and logic to handleAnimals the
                        // signed in test2 can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * method: signIn
     * <p>
     * Creates intent for sign in.
     */
    @Override
    public void signIn() {
        Intent signInIntent = com.google.android.gms.auth.api.Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        ((AppCompatActivity) activity).startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    /**
     * method: signOut
     * <p>
     * Sign out.
     *
     * @param signedOutHandler the signed out handler
     */
    @Override
    public void signOut(final SignedOutHandler signedOutHandler) {
        if (mGoogleApiClient.isConnected()) {
            com.google.android.gms.auth.api.Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (mAuth != null) {
                                // Firebase sign out
                                mAuth.signOut();
                                mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                                    @Override
                                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                        if (firebaseAuth.getCurrentUser() == null)
                                            signedOutHandler.onSignOut();
                                    }
                                });

                            }
                        }
                    });
        } else {
            this.signedOutHandler = signedOutHandler;
            mGoogleApiClient.connect();
            signOut = true;
        }

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
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = com.google.android.gms.auth.api.Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.w(TAG, String.valueOf(result.isSuccess()));
            Log.v(TAG, result.getStatus().toString());
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                firebaseAuthWithGoogle(result.getSignInAccount());
                mAuthListener.setSignedInHandler(signedInHandler);
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (signOut) {
            if (mAuth != null) {
                // Firebase sign out
                com.google.android.gms.auth.api.Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                // Just in case because I am paranoid
                                // Firebase sign out
                                mAuth.signOut();
                                mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                                    @Override
                                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                        if (firebaseAuth.getCurrentUser() == null)
                                            signedOutHandler.onSignOut();
                                    }
                                });

                            }
                        });

            }
            signOut = false;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //TODO implement function
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
}

