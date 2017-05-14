package com.b5.findfurryfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.b5.findfurryfriends.firebase.handlers.SignedInHandler;
import com.b5.findfurryfriends.firebase.wrappers.FirebaseWrapper;
import com.google.android.gms.common.SignInButton;

/**
 * Login.java
 * Mass Academy Apps for Good - B5
 * April 2017
 */
public class Login extends AppCompatActivity implements SignedInHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.b5.findfurryfriends.R.layout.activity_login);


        final SignInButton signIn = (SignInButton) findViewById(com.b5.findfurryfriends.R.id.sign_in_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseWrapper.getFirebase(Login.this).signIn();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FirebaseWrapper.getFirebase(this).signInOnIntentResult(requestCode, data, this);

    }

    @Override
    public void onSignInSuccess() {
        if (FirebaseWrapper.getFirebase(this).getUser() != null) {
            Intent toSearch = new Intent(Login.this, Search.class);
            startActivity(toSearch);
            finish();
        } else {
            Log.w("LOGIN", "USER IS NULL");
        }
    }
}
