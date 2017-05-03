package com.b5.findfurryfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);


        RVAdapter adapter = new RVAdapter();
        favs = getFavs(adapter);
        adapter.pets = favs;
        rv.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(com.b5.findfurryfriends.R.id.search);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent toSearch = new Intent(Favs.this, MainActivity.class);
                startActivity(toSearch);
            }
        });
    }
    public static List<Animal> getFavs(RVAdapter adapter){
        List<Animal> favourites = adapter.favs;
        return favourites;
    }

}
