package com.b5.findfurryfriends.firebase.wrappers;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.b5.findfurryfriends.firebase.data.Animal;

/**
 * StorageInterface.java
 * Mass Academy Apps for Good - B5
 * May 2017
 * <p>
 * Interface of Firebase Storage functions
 */
interface StorageInterface {

    /**
     * The request code for the {@code createCaptureIntent}.
     */
    int REQUEST_IMAGE_CAPTURE = 1;

    /**
     * method: createCaptureIntent
     * <p>
     * Create image capture intent that will eventually upload {@code animal}.
     *
     * @param animal animal data to be uploaded
     */
    void createCaptureIntent(Animal animal);

    /**
     * method: getImage
     * <p>
     * Gets the bitmap image by and puts it in the ImageView
     *
     * @param name      Name/hash of image
     * @param imageView ImageView to display the image in
     */
    void getImage(String name, ImageView imageView);

    /**
     * method: uploadOnIntentResult
     *
     * @param requestCode the request code in onIntentResult
     * @param resultCode  the result code in onIntentResult
     * @param data        intent data in onIntentResult
     * @return true if upload is successful
     */
    boolean uploadOnIntentResult(int requestCode, int resultCode, Intent data);

    /**
     * method: setContext.
     * <p>
     * Sets context.
     *
     * @param activity the activity
     */
    void setContext(Context activity);


}
