package com.b5.findfurryfriends.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.b5.findfurryfriends.R;

/**
 * Created by jinch on 5/6/2017.
 */
class AnimalViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView age;
    ImageView image;
    TextView desc;
    TextView breed;
    ImageButton more;
    ImageButton save;
    ImageButton remove;

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

    }

}
