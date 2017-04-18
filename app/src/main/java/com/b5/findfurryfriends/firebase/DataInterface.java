package com.b5.findfurryfriends.firebase;

import java.util.List;

/**
 * Created by sampendergast on 4/7/17.
 */

public interface DataInterface {

    List<Animal> fetch(int count);
    void uploadAnimal(Animal animal);


    /**
     * NOT IMPLEMENTED
     * @param tags
     * @return animals
     */
    List<Animal> search(List<String> tags);
}
