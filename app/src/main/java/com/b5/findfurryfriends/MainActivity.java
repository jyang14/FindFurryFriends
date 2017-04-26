package com.b5.findfurryfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.b5.findfurryfriends.firebase.Animal;
import com.b5.findfurryfriends.firebase.FetcherHandler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FetcherHandler {

    List<Animal> pets;
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

        ImageButton more = (ImageButton) findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent moreInfo = new Intent(MainActivity.this, ViewInfo.class);
                startActivity(moreInfo);
            }
        });


        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
      //  pets = makeFakeData();
        pets = new ArrayList<Animal>();
        pets.add(new Animal("Bob","image",5,"fluffy cat", new ArrayList<String>()));
        pets.add(new Animal("Sam","image",7,"big dog", new ArrayList<String>()));
        pets.add(new Animal("Sandy","image",12,"cool lizard", new ArrayList<String>()));
        RVAdapter adapter = new RVAdapter();
        adapter.pets=pets;
        rv.setAdapter(adapter);
    }

    @Override
    public void handle(List<Animal> results) {

    }
    private ArrayList<Animal> makeFakeData(){
        ArrayList<Animal> temp = new ArrayList<Animal>();
        temp.add(new Animal("Bob","image",5,"fluffy cat", new ArrayList<String>()));
        temp.add(new Animal("Sam","image",7,"big dog", new ArrayList<String>()));
        temp.add(new Animal("Sandy","image",12,"cool lizard", new ArrayList<String>()));
        return temp;
    }
}
