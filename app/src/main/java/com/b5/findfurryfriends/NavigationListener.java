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
                if (!(activity instanceof MainActivity)) {
                    Intent toSearch = new Intent(activity, MainActivity.class);
                    activity.startActivity(toSearch);
                    activity.finish();
                    return true;
                }
                break;
            case R.id.navigation_profile:
                if (!(activity instanceof Profile)) {
                    Intent toProfile = new Intent(activity, Profile.class);
                    activity.startActivity(toProfile);
                    return true;
                }
                break;
            case R.id.navigation_favs:
                if (!(activity instanceof Favs)) {
                    Intent toFavs = new Intent(activity, Favs.class);
                    activity.startActivity(toFavs);
                    return true;
                }
                break;
            case R.id.navigation_upload:
                if (!(activity instanceof Upload)) {
                    Intent toUpload = new Intent(activity, Upload.class);
                    activity.startActivity(toUpload);
                    return true;
                }
                break;
        }
        return false;
    }

}
