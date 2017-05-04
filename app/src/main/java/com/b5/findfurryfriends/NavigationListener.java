package com.b5.findfurryfriends;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by jinch on 5/4/2017.
 */
class NavigationListener implements BottomNavigationView.OnNavigationItemSelectedListener {

    private AppCompatActivity activity;

    public NavigationListener(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_search:
                Intent toSearch = new Intent(activity, MainActivity.class);
                activity.startActivity(toSearch);
                return true;
            case R.id.navigation_profile:
                Intent toProfile = new Intent(activity, Profile.class);
                activity.startActivity(toProfile);
                return true;
            case R.id.navigation_favs:
                Intent toFavs = new Intent(activity, Favs.class);
                activity.startActivity(toFavs);
                return true;
            case R.id.navigation_upload:
                Intent toUpload = new Intent(activity, Upload.class);
                activity.startActivity(toUpload);
                return true;
        }
        return false;
    }

}
