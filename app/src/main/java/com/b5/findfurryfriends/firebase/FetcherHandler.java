package com.b5.findfurryfriends.firebase;

import java.util.List;

/**
 * Created by jinch on 4/24/2017.
 */

public interface FetcherHandler {


    void handle(List<Animal> results);

}
