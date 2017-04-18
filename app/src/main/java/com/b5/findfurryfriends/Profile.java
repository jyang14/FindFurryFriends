package com.b5.findfurryfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.b5.findfurryfriends.firebase.FirebaseInstance;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.b5.findfurryfriends.R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(com.b5.findfurryfriends.R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(com.b5.findfurryfriends.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent toSearch = new Intent(Profile.this, MainActivity.class);
                startActivity(toSearch);
            }
        });
        Button account = (Button) findViewById(com.b5.findfurryfriends.R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseInstance.getFirebase(Profile.this).signOut();
                Intent toLogin = new Intent(Profile.this, Login.class);
                startActivity(toLogin);
            }
        });
    }

}
