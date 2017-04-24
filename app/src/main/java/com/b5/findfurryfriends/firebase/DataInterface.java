package com.b5.findfurryfriends.firebase;

import java.util.List;

/**
 * Created by sampendergast on 4/7/17.
 */

public interface DataInterface {

    List<Animal> fetch(int count);
    void uploadAnimal(Animal animal);


    void search(List<String> tags, final FetcherHandler handler);
}
