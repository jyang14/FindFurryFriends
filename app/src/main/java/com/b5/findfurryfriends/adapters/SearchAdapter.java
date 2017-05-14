package com.b5.findfurryfriends.adapters;

import android.util.Log;
import android.view.View;

import com.b5.findfurryfriends.firebase.data.Animal;
import com.b5.findfurryfriends.firebase.data.User;
import com.b5.findfurryfriends.firebase.wrappers.FirebaseWrapper;

import java.util.List;

/**
 * SearchAdapter.java
 * Mass Academy Apps for Good - B5
 * April 2017
 */
public class SearchAdapter extends RVAdapter {

    static private final String TAG = "SEARCHADAPTER";

    /**
     * constructor: SearchAdapter
     * <p>
     * default constructor
     */
    public SearchAdapter() {
    }

    /**
     * method: setPets
     *
     * @param pets the list of pets to be displayed
     */
    @Override
    public void setPets(List<Animal> pets) {

        User user = FirebaseWrapper.getFirebase(context).getUser();
        if (user == null) {
            Log.w(TAG, "ERROR USER IS NULL!");
            return;
        }
        List<Long> favorites = user.favorites;
        if (favorites != null)
            for (int i = pets.size() - 1; i >= 0; i--)
                if (favorites.contains(pets.get(i).animalID))
                    pets.remove(i);

        super.setPets(pets);
    }

    @Override
    public final void onBindViewHolder(final AnimalViewHolder animalViewHolder, int i) {
        super.onBindViewHolder(animalViewHolder, i);

        animalViewHolder.remove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int i = animalViewHolder.getAdapterPosition();
                // Animal removed = pets.get(i); //TODO remove your item from data base
                pets.remove(i);  // remove the item from list
                notifyItemRemoved(i); // notify the adapter about the removed item
            }
        });

        animalViewHolder.save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int i = animalViewHolder.getAdapterPosition();
                FirebaseWrapper.getFirebase(context).addFavorite(pets.get(i));
                pets.remove(i);
                notifyItemRemoved(i);
            }
        });


    }

}
