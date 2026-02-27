package com.FoodManagementSystem.Service;


import com.FoodManagementSystem.Models.FoodItem;
import org.apache.logging.log4j.util.Lazy;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface foodItemService {
    String addFoodItem(Integer restaurantId, FoodItem foodItem, MultipartFile image);
    FoodItem updateFoodItem(Integer foodItemId, FoodItem updatedFoodItem, MultipartFile image);
    String deleteFoodItem(Integer foodItemId);
    List<FoodItem> getFoodItemsByRestaurantId(Integer restaurantId);

}
