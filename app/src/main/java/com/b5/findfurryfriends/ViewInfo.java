package com.b5.findfurryfriends;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.b5.findfurryfriends.firebase.FirebaseWrapper;
import com.b5.findfurryfriends.firebase.data.Animal;

public class ViewInfo extends AppCompatActivity {

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
//        FloatingActionButton search = (FloatingActionButton) findViewById(com.b5.findfurryfriends.R.id.search);
//        search.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent toSearch = new Intent(ViewInfo.this, Search.class);
//                startActivity(toSearch);
//            }
//        });
    }

}
