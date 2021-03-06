package com.b5.findfurryfriends.adapters;

import android.view.View;

import com.b5.findfurryfriends.firebase.data.Animal;
import com.b5.findfurryfriends.firebase.wrappers.FirebaseWrapper;

/**
 * FavoriteAdapter.java
 * Mass Academy Apps for Good - B5
 * May 2017
 */
public class FavoriteAdapter extends RVAdapter {

    /**
     * constructor: FavoriteAdapter
     * <p>
     * default constructor. empty.
     */
    public FavoriteAdapter() {

    }

    @Override
    public final void onBindViewHolder(final AnimalViewHolder animalViewHolder, int i) {
        super.onBindViewHolder(animalViewHolder, i);

        animalViewHolder.remove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int i = animalViewHolder.getAdapterPosition();
                Animal removed = pets.get(i);
                FirebaseWrapper.getFirebase(context).removeFavorite(removed);
                pets.remove(i);  // remove the item from list
                notifyItemRemoved(i); // notify the adapter about the removed item
            }
        });

        animalViewHolder.save.setVisibility(View.INVISIBLE);

    }
}
