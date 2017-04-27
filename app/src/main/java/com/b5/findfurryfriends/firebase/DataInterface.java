package com.b5.findfurryfriends.firebase;

import com.b5.findfurryfriends.firebase.data.Animal;

import java.util.List;

/**
 * Created by sampendergast on 4/7/17.
 */

public interface DataInterface {

    List<Animal> fetch(int count);
    void uploadAnimal(Animal animal);


    void search(List<String> tags, FetcherHandler handler);
}
