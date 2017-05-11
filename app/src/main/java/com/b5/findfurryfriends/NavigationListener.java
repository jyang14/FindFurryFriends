package com.b5.findfurryfriends;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/** NavigationListener.java
 *  Mass Academy Apps for Good - B5
 *  May 2017
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
                if (!(activity instanceof Search)) {
                    Intent toSearch = new Intent(activity, Search.class);
                    activity.startActivity(toSearch);
                    activity.finish();
                    activity.overridePendingTransition(0, 0);
                    return true;
                }
                break;
            case R.id.navigation_profile:
                if (!(activity instanceof Profile)) {
                    Intent toProfile = new Intent(activity, Profile.class);
                    activity.startActivity(toProfile);
                    activity.finish();
                    activity.overridePendingTransition(0, 0);
                    return true;
                }
                break;
            case R.id.navigation_favs:
                if (!(activity instanceof Favs)) {
                    Intent toFavs = new Intent(activity, Favs.class);
                    activity.startActivity(toFavs);
                    activity.finish();
                    activity.overridePendingTransition(0, 0);
                    return true;
                }
                break;
            case R.id.navigation_upload:
                if (!(activity instanceof Upload)) {
                    Intent toUpload = new Intent(activity, Upload.class);
                    activity.startActivity(toUpload);
                    activity.finish();
                    activity.overridePendingTransition(0, 0);
                    return true;
                }
                break;
        }
        return false;
    }

}
