package com.b5.findfurryfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
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
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_upload);
        navigation.setOnNavigationItemSelectedListener(new NavigationListener(this));

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
                String breed = ((TextView) findViewById(R.id.breedEntry)).getText().toString();
                if (name.equals(""))
                    name = "No breed";
                FirebaseWrapper.getFirebase(Upload.this).createCaptureIntent(new Animal(name, null, age, description, null, breed));

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent toSearch = new Intent(this, MainActivity.class);
        this.startActivity(toSearch);
        this.finish();
        this.overridePendingTransition(0, 0);
    }

}