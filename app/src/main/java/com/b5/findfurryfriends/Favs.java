package com.b5.findfurryfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.b5.findfurryfriends.firebase.data.Animal;

import java.util.List;

public class Favs extends AppCompatActivity {

    List<Animal> favs;

    public static List<Animal> getFavs(RVAdapter adapter) {
        List<Animal> favourites = adapter.favs;
        return favourites;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.b5.findfurryfriends.R.layout.activity_favs);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_favs);
        navigation.setOnNavigationItemSelectedListener(new NavigationListener(this));
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

    @Override
    public void onBackPressed() {
        Intent toSearch = new Intent(this, MainActivity.class);
        this.startActivity(toSearch);
        this.finish();
        this.overridePendingTransition(0, 0);
    }

}
