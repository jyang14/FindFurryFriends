package com.b5.findfurryfriends.firebase;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.b5.findfurryfriends.firebase.data.Animal;

/**
 * Created by jinch on 5/3/2017.
 */

interface StorageInterface {

    int REQUEST_IMAGE_CAPTURE = 1;

    void createCaptureIntent(Animal animal);

    void getImage(String name, ImageView imageView);

    boolean uploadOnIntentResult(int requestCode, int resultCode, Intent data);

    void setContext(Context activity);


}
