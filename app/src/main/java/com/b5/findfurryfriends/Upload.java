package com.b5.findfurryfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.b5.findfurryfriends.firebase.FirebaseWrapper;
import com.b5.findfurryfriends.firebase.data.Animal;

public class Upload extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.b5.findfurryfriends.R.layout.activity_upload);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(com.b5.findfurryfriends.R.id.search);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent toSearch = new Intent(Upload.this, MainActivity.class);
                startActivity(toSearch);
            }
        });

        Button submit = (Button) findViewById(com.b5.findfurryfriends.R.id.upload);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseWrapper instant = FirebaseWrapper.getFirebase(Upload.this);
                try {
                    int age = Integer.parseInt(((TextView) (findViewById(R.id.ageEntry))).getText().toString());
                    instant.uploadAnimal(new Animal(((TextView) findViewById(R.id.nameEntry)).getText().toString(), null, age, ((TextView) findViewById(R.id.infoEntry)).getText().toString(), null));
                    //this will take all information entered and put it in database
                    //need a method to take all information -> how do the guys want it stored?
                    TextView tester = (TextView) findViewById(R.id.textView);
                    tester.setText(R.string.submitted);
                } catch (NumberFormatException e) {
                    instant.uploadAnimal(new Animal(((TextView) findViewById(R.id.nameEntry)).getText().toString(), null, -1, ((TextView) findViewById(R.id.infoEntry)).getText().toString(), null));
                    TextView tester = (TextView) findViewById(R.id.textView);
                    tester.setText(R.string.submitted);
                }
            }
        });
    }

}