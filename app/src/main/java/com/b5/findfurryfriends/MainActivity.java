package com.b5.findfurryfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.b5.findfurryfriends.R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(com.b5.findfurryfriends.R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(com.b5.findfurryfriends.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, com.b5.findfurryfriends.R.string.navigation_drawer_open, com.b5.findfurryfriends.R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(com.b5.findfurryfriends.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final ImageButton more = (ImageButton) findViewById(com.b5.findfurryfriends.R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewInfo.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(com.b5.findfurryfriends.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.b5.findfurryfriends.R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.b5.findfurryfriends.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent changeScreen = new Intent();
        if (id == com.b5.findfurryfriends.R.id.nav_favs) {
            changeScreen = new Intent(MainActivity.this, Favs.class);
        } else if (id == com.b5.findfurryfriends.R.id.nav_profile) {
            changeScreen = new Intent(MainActivity.this, Profile.class);
        } else if (id == com.b5.findfurryfriends.R.id.nav_upload) {
            changeScreen = new Intent(MainActivity.this, Upload.class);
        } else if (id == com.b5.findfurryfriends.R.id.nav_search) {
            changeScreen = new Intent(MainActivity.this, MainActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(com.b5.findfurryfriends.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        startActivity(changeScreen);
        return true;
    }
}
