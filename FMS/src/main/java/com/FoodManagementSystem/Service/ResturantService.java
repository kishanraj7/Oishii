package com.FoodManagementSystem.Service;
import com.FoodManagementSystem.Models.Resturant;

import java.util.List;

public interface ResturantService {
    String createRestaurant(Resturant resturant);
    Resturant updateResturant(Integer restaurantId, Resturant restaurant);
    String deleteResturant(Integer restaurantId);
    List<Resturant> getResturantByOwnerId(Integer ownerId);
}
