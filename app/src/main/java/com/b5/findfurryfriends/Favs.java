package com.b5.findfurryfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.b5.findfurryfriends.firebase.data.Animal;

import java.util.ArrayList;
import java.util.List;

public class Favs extends AppCompatActivity {

    List<Animal> favs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.b5.findfurryfriends.R.layout.activity_favs);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_favs);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("Favourites");

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);


        RVAdapter adapter = new RVAdapter();
        favs = getFavs(adapter);
        adapter.pets = favs;
        rv.setAdapter(adapter);


    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_favs:
                    Intent toFavs = new Intent(Favs.this, Favs.class);
                    startActivity(toFavs);
                    return true;
                case R.id.navigation_search:
                    Intent toSearch = new Intent(Favs.this, MainActivity.class);
                    startActivity(toSearch);
                    return true;
                case R.id.navigation_profile:
                    Intent toProfile = new Intent(Favs.this, Profile.class);
                    startActivity(toProfile);
                    return true;

                case R.id.navigation_upload:
                    Intent toUpload = new Intent(Favs.this, Upload.class);
                    startActivity(toUpload);
                    return true;
            }
            return false;
        }

    };
    public static List<Animal> getFavs(RVAdapter adapter){
        List<Animal> favourites = adapter.favs;
        return favourites;
    }

}
