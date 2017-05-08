package com.b5.findfurryfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.b5.findfurryfriends.firebase.FirebaseWrapper;

public class Profile extends AppCompatActivity {

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
                FirebaseWrapper.getFirebase(Profile.this).signOut();
                Intent toLogin = new Intent(Profile.this, Login.class);
                startActivity(toLogin);
                finish();
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

}
