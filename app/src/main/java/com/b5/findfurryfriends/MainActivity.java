package com.b5.findfurryfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.b5.findfurryfriends.firebase.Animal;
import com.b5.findfurryfriends.firebase.FetcherHandler;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FetcherHandler {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    Intent toSearch = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(toSearch);
                    return true;
                case R.id.navigation_profile:
                    Intent toProfile = new Intent(MainActivity.this, Profile.class);
                    startActivity(toProfile);
                    return true;
                case R.id.navigation_favs:
                    Intent toFavs = new Intent(MainActivity.this, Favs.class);
                    startActivity(toFavs);
                    return true;
                case R.id.navigation_upload:
                    Intent toUpload = new Intent(MainActivity.this, Upload.class);
                    startActivity(toUpload);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("Search");

    }

    @Override
    public void handle(List<Animal> results) {

    }
}
