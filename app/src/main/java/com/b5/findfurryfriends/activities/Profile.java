package com.b5.findfurryfriends.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.b5.findfurryfriends.R;
import com.b5.findfurryfriends.firebase.handlers.SignedOutHandler;
import com.b5.findfurryfriends.firebase.wrappers.FirebaseWrapper;

/**
 * Profile.java
 * Mass Academy Apps for Good - B5
 * April 2017
 */
public class Profile extends AppCompatActivity implements SignedOutHandler {

    private static final String TAG = "PROFILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.b5.findfurryfriends.R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(com.b5.findfurryfriends.R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_profile);
        navigation.setOnNavigationItemSelectedListener(new NavigationListener(this));

        Button account = (Button) findViewById(com.b5.findfurryfriends.R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseWrapper.getFirebase(Profile.this).signOut(Profile.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent toSearch = new Intent(this, Search.class);
        startActivity(toSearch);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onSignOut() {
        Log.v(TAG, "Sign Out called");
        Intent toLogin = new Intent(Profile.this, Login.class);
        startActivity(toLogin);
        finish();
    }
}
