package com.b5.findfurryfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.b5.findfurryfriends.firebase.FirebaseWrapper;
import com.google.android.gms.common.SignInButton;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.b5.findfurryfriends.R.layout.activity_login);


        final SignInButton signIn = (SignInButton) findViewById(com.b5.findfurryfriends.R.id.sign_in_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseWrapper.getFirebase(Login.this).signIn();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (FirebaseWrapper.getFirebase(this).signInOnIntentResult(requestCode, data)) {
            Intent toSearch = new Intent(Login.this, MainActivity.class);
            startActivity(toSearch);
        }
    }
}
