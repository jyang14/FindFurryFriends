package com.b5.findfurryfriends.firebase.handlers;

import com.b5.findfurryfriends.firebase.data.Animal;

import java.util.List;

public interface FetchAnimalHandler {

    void handle(List<Animal> results);

}
