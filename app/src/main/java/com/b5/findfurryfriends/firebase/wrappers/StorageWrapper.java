package com.b5.findfurryfriends.firebase.wrappers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.b5.findfurryfriends.firebase.data.Animal;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * StorageWrapper.java
 * Mass Academy Apps for Good - B5
 * April 2017
 * <p>
 * Implementation of Firebase Database functions
 */
class StorageWrapper implements StorageInterface {

    private static final String TAG = "STORAGE";

    private final FirebaseStorage firebaseStorage;
    private Context activity;
    private Animal animal;


    /**
     * Instantiates a new Storage wrapper.
     *
     * @param activity the activity
     */
    StorageWrapper(Context activity) {
        firebaseStorage = FirebaseStorage.getInstance();
        this.activity = activity;
    }

    /**
     * method: computeSHA256
     * <p>
     * StackOverflow tells me that SHA-256 collisions are pretty rare.
     * http://stackoverflow.com/questions/4014090/is-it-safe-to-ignore-the-possibility-of-sha-collisions-in-practice
     * Therefore I am using this algorithm to name my images because I'm lazy.
     *
     * @param bytes the bytes of image to computer SHA256 of
     * @return the hash of the image
     */
    private String computeSHA256(byte[] bytes) {
        String output;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(bytes);
            BigInteger bigInt = new BigInteger(1, hash);
            output = bigInt.toString(16);
            while (output.length() < 32) {
                output = "0" + output;
            }


            return (output + ".jpg");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return String.valueOf(System.nanoTime());
        }
    }

    /**
     * method: createCaptureIntent
     * <p>
     * Create image capture intent that will eventually upload {@code animal}.
     *
     * @param animal animal data to be uploaded
     */
    @Override
    public void createCaptureIntent(Animal animal) {
        this.animal = animal;
        Log.v(TAG, this.animal.toString());
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null)
            ((AppCompatActivity) activity).startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    /**
     * method: getImage
     * <p>
     * Gets the bitmap image by and puts it in the ImageView
     *
     * @param name      Name/hash of image
     * @param imageView ImageView to display the image in
     */
    @Override
    public void getImage(String name, ImageView imageView) {
        StorageReference storageRef = firebaseStorage.getReference().child(name);

        Glide.with(activity)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(imageView);
    }

    /**
     * method: uploadOnIntentResult
     *
     * @param requestCode the request code in onIntentResult
     * @param resultCode  the result code in onIntentResult
     * @param data        intent data in onIntentResult
     * @return true if upload is successful
     */
    @Override
    public boolean uploadOnIntentResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            if (animal != null) {
                Log.v(TAG, this.animal.toString());
            } else {
                Log.v(TAG, "Animal is null");
            }

            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            // Android studio is paranoid
            if (bitmap != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                bitmap.recycle();
                byte[] bytes = byteArrayOutputStream.toByteArray();
                String name = computeSHA256(bytes);

                StorageReference storageRef = firebaseStorage.getReference();

                storageRef.child(name).putBytes(bytes);
                animal.image = name;
                FirebaseWrapper.getFirebase(activity).uploadAnimal(animal);
                return true;
            }
        }
        return false;
    }

    /**
     * method: setContext.
     * <p>
     * Sets context.
     *
     * @param activity the activity
     */
    @Override
    public void setContext(Context activity) {
        this.activity = activity;
    }

}
