package com.b5.findfurryfriends;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.b5.findfurryfriends.firebase.FirebaseWrapper;
import com.b5.findfurryfriends.firebase.data.Animal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grayb on 4/26/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.AnimalViewHolder> {
    List<Animal> pets;
    View.OnClickListener clickListener;
    List<Animal> favs;
    private Context context;

    public RVAdapter(List<Animal> pets) {
        this.pets = pets;
        favs = new ArrayList<>();

    }

    public RVAdapter() {
        this.pets = new ArrayList<>();
        favs = new ArrayList<>();
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
    public final void onBindViewHolder(final AnimalViewHolder animalViewHolder, int i) {
        Animal pet = pets.get(i);
        animalViewHolder.name.setText(pet.name);
        animalViewHolder.age.setText(String.valueOf(pet.age));
        animalViewHolder.desc.setText(String.valueOf(pet.description));

        if (pet.image != null && pet.image.contains(".jpg")) {
            FirebaseWrapper.getFirebase(null).getImage(context, pet.image, animalViewHolder.image);
            // animalViewHolder.image.setImageResource(pets.get(i).photoId);
        }

        animalViewHolder.remove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int i = animalViewHolder.getAdapterPosition();
               // Animal removed = pets.get(i); // remove your item from data base
                pets.remove(i);  // remove the item from list
                notifyItemRemoved(i); // notify the adapter about the removed item
            }
        });

        animalViewHolder.save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int i = animalViewHolder.getAdapterPosition();
                favs.add(pets.get(i));
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class AnimalViewHolder extends RecyclerView.ViewHolder {
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
}
