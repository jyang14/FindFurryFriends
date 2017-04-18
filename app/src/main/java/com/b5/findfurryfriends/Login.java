package com.b5.findfurryfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.b5.findfurryfriends.firebase.FirebaseInstance;
import com.google.android.gms.common.SignInButton;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.b5.findfurryfriends.R.layout.activity_login);


        FloatingActionButton bypass = (FloatingActionButton) findViewById(com.b5.findfurryfriends.R.id.bypass);
        bypass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent toSearch = new Intent(Login.this, MainActivity.class);
                startActivity(toSearch);
            }
        });

        final SignInButton signIn = (SignInButton) findViewById(com.b5.findfurryfriends.R.id.sign_in_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseInstance.getFirebase(Login.this).signIn();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(FirebaseInstance.getFirebase(this).onActivityResult(requestCode,resultCode,data)) {
            Intent toSearch = new Intent(Login.this, MainActivity.class);
            startActivity(toSearch);
        }
    }
}
