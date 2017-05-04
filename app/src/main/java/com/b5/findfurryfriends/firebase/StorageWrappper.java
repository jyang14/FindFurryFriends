package com.b5.findfurryfriends.firebase;

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
 * Created by jinch on 5/3/2017.
 */

class StorageWrappper implements StorageInterface {

    private static final String TAG = "STORAGE";

    FirebaseStorage firebaseStorage;
    AppCompatActivity activity;
    Animal animal;


    StorageWrappper(AppCompatActivity activity) {
        firebaseStorage = FirebaseStorage.getInstance();
        this.activity = activity;
    }

    // StackOverflow tells me that SHA-256 collisions are pretty rare.
    // http://stackoverflow.com/questions/4014090/is-it-safe-to-ignore-the-possibility-of-sha-collisions-in-practice
    // Therefore I am using this algorithm to name my images because I'm lazy.
    private String conputerSHA(byte[] bytes) {
        String output;
        int read;
        byte[] buffer = new byte[8192];

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(bytes);
            BigInteger bigInt = new BigInteger(1, hash);
            output = bigInt.toString(16);
            while (output.length() < 32) {
                output = "0" + output;
            }


            return (output + ".jpg");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void createCaptureIntent(Animal animal) {
        this.animal = animal;
        Log.v(TAG, this.animal.toString());
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null)
            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void getImage(Context context, String name, ImageView imageView) {
        StorageReference storageRef = firebaseStorage.getReference().child(name);

        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(imageView);
    }

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
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bytes = baos.toByteArray();
            String name = conputerSHA(bytes);

            StorageReference storageRef = firebaseStorage.getReference();

            storageRef.child(name).putBytes(bytes);
            animal.image = name;
            FirebaseWrapper.getFirebase(activity).uploadAnimal(animal);
            return true;

        }
        return false;
    }

    @Override
    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

}
