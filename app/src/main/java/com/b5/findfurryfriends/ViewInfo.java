package com.b5.findfurryfriends;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.b5.findfurryfriends.firebase.FirebaseWrapper;
import com.b5.findfurryfriends.firebase.data.Animal;
import com.b5.findfurryfriends.firebase.data.User;
import com.b5.findfurryfriends.firebase.handlers.FetchUserHandler;

public class ViewInfo extends AppCompatActivity implements FetchUserHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.b5.findfurryfriends.R.layout.activity_view_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Animal animal = getIntent().getExtras().getParcelable("animal");
        setTitle(animal.name);
        ((TextView) findViewById(R.id.info)).setText(animal.description);
        FirebaseWrapper.getFirebase(this).getImage(animal.image, (ImageView) findViewById(R.id.pic1));
        FirebaseWrapper.getFirebase(this).getUserFromAnimal(animal, this);
//        FloatingActionButton search = (FloatingActionButton) findViewById(com.b5.findfurryfriends.R.id.search);
//        search.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent toSearch = new Intent(ViewInfo.this, Search.class);
//                startActivity(toSearch);
//            }
//        });
    }

    @Override
    public void handleUser(User user) {
        ((TextView) findViewById(R.id.user_email)).setText("Contact: " + user.contact);
    }
}
