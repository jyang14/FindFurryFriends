package com.b5.findfurryfriends.firebase;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by jinch on 4/14/2017.
 */

public class FirebaseInstance {
    private static FirebaseInterface instance;

    public static FirebaseInterface getFirebase(AppCompatActivity activity){
        if(instance!=null){
            instance.setActivity(activity);
            return instance;
        }else{
            instance = new FirebaseWrapper(activity);
            return instance;
        }
    }

}
