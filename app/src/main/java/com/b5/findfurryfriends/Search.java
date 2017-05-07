package com.b5.findfurryfriends;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.b5.findfurryfriends.adapters.SearchAdapter;
import com.b5.findfurryfriends.firebase.FirebaseWrapper;
import com.b5.findfurryfriends.firebase.data.Animal;
import com.b5.findfurryfriends.firebase.handlers.FetcherHandler;

import java.util.Arrays;
import java.util.List;

public class Search extends AppCompatActivity implements FetcherHandler {

    static private final String TAG = "SEARCH";

    private SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new NavigationListener(this));
        setTitle("Search");

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        adapter = new SearchAdapter();
        rv.setAdapter(adapter);
        FirebaseWrapper.getFirebase(this).search(null, this);
    }

    @Override
    public void handle(List<Animal> results) {
        Log.v(TAG, Arrays.deepToString(results.toArray()));
        adapter.setPets(results);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new Dialog.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Search.super.onBackPressed();
                    }
                }).create().show();
    }

}
