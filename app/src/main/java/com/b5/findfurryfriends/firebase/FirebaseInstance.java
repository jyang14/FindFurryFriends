package com.b5.findfurryfriends.firebase;

import android.support.v7.app.AppCompatActivity;

public class FirebaseInstance {

    //Lazy singleton
    private static FirebaseWrapper instance;

    public static FirebaseWrapper getFirebase(AppCompatActivity activity) {
        if(instance!=null){
            instance.setActivity(activity);
        }else{
            instance = new FirebaseWrapper(activity);
        }
        return instance;

    }

}
