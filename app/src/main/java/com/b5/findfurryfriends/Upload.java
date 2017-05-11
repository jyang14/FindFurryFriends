package com.b5.findfurryfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.b5.findfurryfriends.firebase.FirebaseWrapper;
import com.b5.findfurryfriends.firebase.data.Animal;

/** Upload.java
 *  Mass Academy Apps for Good - B5
 *  April 2017
 */
public class Upload extends AppCompatActivity {

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (FirebaseWrapper.getFirebase(this).uploadOnIntentResult(requestCode, resultCode, data)) {

            TextView tester = (TextView) findViewById(R.id.textView);
            tester.setText(R.string.submitted);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.b5.findfurryfriends.R.layout.activity_upload);

        Toolbar toolbar = (Toolbar) findViewById(com.b5.findfurryfriends.R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_upload);
        navigation.setOnNavigationItemSelectedListener(new NavigationListener(this));

        Button submit = (Button) findViewById(com.b5.findfurryfriends.R.id.upload);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean valid = true;
                String name = ((TextView) findViewById(R.id.nameEntry)).getText().toString();
                if (name.equals(""))
                    valid = false;

                String description = ((TextView) findViewById(R.id.infoEntry)).getText().toString();
                if (description.equals(""))
                    valid = false;

                int age = -1;
                try {
                    age = Integer.parseInt(((TextView) (findViewById(R.id.ageEntry))).getText().toString());
                } catch (NumberFormatException e) {
                    valid = false;
                }
                String breed = ((TextView) findViewById(R.id.breedEntry)).getText().toString();
                if (breed.equals(""))
                    valid = false;

                String type = ((TextView) findViewById(R.id.animalType)).getText().toString();
                if (type.equals(""))
                    valid = false;

                if(valid)
                FirebaseWrapper.getFirebase(Upload.this).createCaptureIntent(new Animal(name, age, description, null, breed, type));

                else{
                    new AlertDialog.Builder(Upload.this)
                            .setTitle("Error")
                            .setMessage("Mandatory fields are empty!")
                            .setNeutralButton("OK",null).create().show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent toSearch = new Intent(this, Search.class);
        this.startActivity(toSearch);
        this.finish();
        this.overridePendingTransition(0, 0);
    }

}