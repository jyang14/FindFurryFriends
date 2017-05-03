package com.b5.findfurryfriends.firebase;

import com.b5.findfurryfriends.firebase.data.Animal;
import com.b5.findfurryfriends.firebase.data.User;

import java.util.List;

interface DataInterface {
    User getUser();

    void setUser(User user);

    void uploadAnimal(Animal animal);

    void search(List<String> tags, FetcherHandler handler);
}
