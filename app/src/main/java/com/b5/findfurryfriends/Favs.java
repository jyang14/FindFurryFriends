package com.b5.findfurryfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.b5.findfurryfriends.adapters.FavoriteAdapter;
import com.b5.findfurryfriends.firebase.FirebaseWrapper;
import com.b5.findfurryfriends.firebase.data.Animal;
import com.b5.findfurryfriends.firebase.handlers.FetchAnimalHandler;

import java.util.List;

/** Favs.java
 *  Mass Academy Apps for Good - B5
 *  April 2017
 */
public class Favs extends AppCompatActivity implements FetchAnimalHandler {
    private FavoriteAdapter adapter;

//    List<Animal> favs;
//
//    public static List<Animal> getFavs(SearchAdapter adapter) {
//        List<Animal> favourites = adapter.getFavs();
//        return favourites;
//    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.b5.findfurryfriends.R.layout.activity_favs);

        Toolbar toolbar = (Toolbar) findViewById(com.b5.findfurryfriends.R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Favorites");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_favs);
        navigation.setOnNavigationItemSelectedListener(new NavigationListener(this));


        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);


        adapter = new FavoriteAdapter();
//        favs = getFavs(adapter);
//        adapter.setFavs(favs);
        rv.setAdapter(adapter);
        FirebaseWrapper.getFirebase(this).getFavorites(this);

    }

    /**
     *
     */
    @Override
    public void onBackPressed() {
        Intent toSearch = new Intent(this, Search.class);
        startActivity(toSearch);
        finish();
        overridePendingTransition(0, 0);
    }

    /**
     *
     * @param results
     */
    @Override
    public void handle(List<Animal> results) {
        adapter.setPets(results);
    }
}
