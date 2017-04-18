package com.b5.findfurryfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class ViewInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.b5.findfurryfriends.R.layout.activity_view_info);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton search = (FloatingActionButton) findViewById(com.b5.findfurryfriends.R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Log.i("Tamsin","in onclick method viewinfo search");
                    Intent toSearch = new Intent(ViewInfo.this, MainActivity.class);
                    startActivity(toSearch);
            }
        });
    }

}
