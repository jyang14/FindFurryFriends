package com.b5.findfurryfriends.firebase.handlers;

import com.b5.findfurryfriends.firebase.data.Animal;

import java.util.List;

public interface FetcherHandler {

    void handle(List<Animal> results);

}