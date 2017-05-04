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
                String name = ((TextView) findViewById(R.id.nameEntry)).getText().toString();
                if (name.equals(""))
                    name = "No Name";

                String description = ((TextView) findViewById(R.id.infoEntry)).getText().toString();
                if (description.equals(""))
                    description = "No Description Submitted";

                int age;
                try {
                    age = Integer.parseInt(((TextView) (findViewById(R.id.ageEntry))).getText().toString());
                } catch (NumberFormatException e) {
                    age = -1;
                }
                FirebaseWrapper.getFirebase(Upload.this).createCaptureIntent(new Animal(name, null, age, description, null));

            }
        });
    }

}