package com.b5.findfurryfriends.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.b5.findfurryfriends.R;

/**
 * AnimalViewHolder.java
 * Mass Academy Apps for Good - B5
 * May 2017
 */
class AnimalViewHolder extends RecyclerView.ViewHolder {
    /**
     * The Name.
     */
    final TextView name;
    /**
     * The Age.
     */
    final TextView age;
    /**
     * The Image.
     */
    final ImageView image;
    /**
     * The Desc.
     */
    final TextView desc;
    /**
     * The Breed.
     */
    final TextView breed;
    /**
     * The Type.
     */
    final TextView type;
    /**
     * The More.
     */
    final ImageButton more;
    /**
     * The Save.
     */
    final ImageButton save;
    /**
     * The Remove.
     */
    final ImageButton remove;

    /**
     * Instantiates a new Animal view holder.
     *
     * @param itemView the item view
     */
    AnimalViewHolder(View itemView) {
        super(itemView);
        age = (TextView) itemView.findViewById(R.id.age);
        name = (TextView) itemView.findViewById(R.id.name);
        image = (ImageView) itemView.findViewById(R.id.img);
        desc = (TextView) itemView.findViewById(R.id.desc);
        more = (ImageButton) itemView.findViewById(R.id.more);
        save = (ImageButton) itemView.findViewById(R.id.save);
        remove = (ImageButton) itemView.findViewById(R.id.delete);
        breed = (TextView) itemView.findViewById(R.id.breed);
        type = (TextView) itemView.findViewById(R.id.type);

    }

}
