package com.b5.findfurryfriends.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.b5.findfurryfriends.R;
import com.b5.findfurryfriends.firebase.FirebaseWrapper;
import com.b5.findfurryfriends.firebase.data.Animal;

import java.util.ArrayList;
import java.util.List;

/** RVAdapter.java
 *  Mass Academy Apps for Good - B5
 *  May 2017
 */
abstract class RVAdapter extends RecyclerView.Adapter<AnimalViewHolder> {

    private final static String TAG = "RVADAPTER";

    List<Animal> pets;
    Context context;

    RVAdapter() {
        pets = new ArrayList<>();
    }


    public void setPets(List<Animal> pets) {
        this.pets = pets;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    @Override
    public AnimalViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        return new AnimalViewHolder(v);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final AnimalViewHolder animalViewHolder, int i) {
        Animal pet = pets.get(i);
        Log.v(TAG, String.format("%d : %s", i, pet == null ? "Null" : pet.toString()));

        animalViewHolder.type.setText(String.valueOf(pet.type));
        animalViewHolder.name.setText(pet.name);
        animalViewHolder.age.setText(String.valueOf(pet.age));
        animalViewHolder.desc.setText(String.valueOf(pet.description));
        animalViewHolder.breed.setText(pet.breed);

        if (pet.image != null && pet.image.contains(".jpg")) {
            FirebaseWrapper.getFirebase(context).getImage(pet.image, animalViewHolder.image);
        }

    }
}
