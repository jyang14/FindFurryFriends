package com.b5.findfurryfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.b5.findfurryfriends.firebase.Animal;
import com.b5.findfurryfriends.firebase.FirebaseInstance;
import com.b5.findfurryfriends.firebase.FirebaseInterface;

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
                FirebaseInterface instant = FirebaseInstance.getFirebase(Upload.this);
                instant.uploadAnimal(new Animal(((TextView)findViewById(R.id.nameEntry)).getText().toString(),null,12,null,null,0,0));
                //this just changes the text - does not actually mean anything
                TextView tester = (TextView) findViewById(com.b5.findfurryfriends.R.id.textView);
                tester.setText("submitted");
            }
        });
    }

}
