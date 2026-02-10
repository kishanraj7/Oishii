package com.FoodManagementSystem.Service;


import com.FoodManagementSystem.Models.FoodItem;

public interface foodItemService {
    void addFoodItem(Integer restaurantId, FoodItem foodItem);
    void updateFoodItem(Integer foodItemId, FoodItem foodItem);
    void deleteFoodItem(Integer foodItemId);
    void getFoodItemsByRestaurantId(Integer restaurantId);
}
