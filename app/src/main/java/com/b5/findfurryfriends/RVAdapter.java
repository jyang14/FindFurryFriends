package com.b5.findfurryfriends;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.b5.findfurryfriends.firebase.data.Animal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grayb on 4/26/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.AnimalViewHolder> {
    List<Animal> pets;

    public RVAdapter(List<Animal> pets) {
        this.pets = pets;
    }

    public RVAdapter() {
        this.pets = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    @Override
    public AnimalViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new AnimalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AnimalViewHolder animalViewHolder, int i) {
        animalViewHolder.name.setText(pets.get(i).name);
        animalViewHolder.age.setText(pets.get(i).age);
        // animalViewHolder.image.setImageResource(pets.get(i).photoId);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class AnimalViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        TextView age;
        ImageView image;

        AnimalViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            age = (TextView) itemView.findViewById(R.id.age);
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.img);

        }


    }
}
