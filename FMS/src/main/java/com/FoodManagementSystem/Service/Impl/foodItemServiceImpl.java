package com.FoodManagementSystem.Service.Impl;

import com.FoodManagementSystem.Models.FoodItem;
import com.FoodManagementSystem.Repositories.ResturantRepo;
import com.FoodManagementSystem.Repositories.foodItemsRepo;
import com.FoodManagementSystem.Service.foodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class foodItemServiceImpl implements foodItemService {
    @Autowired
    private foodItemsRepo foodItemRepo;
    @Autowired
    private ResturantRepo repo;
    @Override
    public void addFoodItem(Integer restaurantId, FoodItem foodItem) {
        if (restaurantId == null) {
            throw new IllegalArgumentException("Restaurant ID cannot be null");
        }
        if (foodItem.getName() == null || foodItem.getName().isEmpty()) {
            throw new IllegalArgumentException("Food item name cannot be null or empty");
        }
        if (foodItem.getPrice() <= 0) {
            throw new IllegalArgumentException("Price cannot be Zero Or Negative");
        }
        if(repo.existsByRid(restaurantId)==true) {
            foodItemRepo.save(foodItem);
            System.out.println("Item Added To Your Menu");
        }
        else {
            System.out.println("The Restaurant ID Entered Is In Valid");
        }
    }

    @Override
    public void updateFoodItem(Integer foodItemId, FoodItem foodItem) {
        if (foodItemId == null) {
            throw new IllegalArgumentException("Food item ID cannot be empty");
        }
        if (foodItem == null) {
            throw new IllegalArgumentException("Food item cannot be null");
        }
        FoodItem existingItem = foodItemRepo.findByFoodId(foodItemId);
        if (existingItem != null) {
            existingItem.setName(foodItem.getName());
            existingItem.setDescription(foodItem.getDescription());
            existingItem.setPrice(foodItem.getPrice());
            if (foodItem.getPrice() <= 0) {
                throw new IllegalArgumentException("Price cannot be Zero Or negative");
            }
            existingItem.setAvailability(foodItem.isAvailability());
            foodItemRepo.save(existingItem); //Update Items In Repo
            System.out.println("Menu Item Updated Successfully");
        } else {
            System.out.println("Food Item Not Found");
        }
    }

    @Override
    public void deleteFoodItem(Integer foodItemId) {
        if (foodItemId == null) {
            throw new IllegalArgumentException("Food item ID cannot be empty");
        }
        if (foodItemRepo.findByFoodId(foodItemId) != null) {
            foodItemRepo.deleteByFoodId(foodItemId);
            System.out.println("Food Item Deletion Successfully");
        } else {
            System.out.println("Food Item Not Found");
        }
    }

    @Override
    public void getFoodItemsByRestaurantId(Integer restaurantId) {
        if (restaurantId == null) {
            throw new IllegalArgumentException("Restaurant ID cannot be null");
        }
        List<FoodItem> foodItemList = foodItemRepo.findAll();
        for (FoodItem foodItem : foodItemList) {
            if (repo.existsByRid(restaurantId)) {
                System.out.println("Food Item ID: " + foodItem.getFoodId());
                System.out.println("Food Item Name: " + foodItem.getName());
                System.out.println("Description: " + foodItem.getDescription());
                System.out.println("Price: " + foodItem.getPrice());
                System.out.println("Availability: " + (foodItem.isAvailability() ? "Available" : "Not Available"));
                System.out.println("\n");
            } else {
                System.out.println("The Restaurant ID Entered Is In Correct");
            }
        }

    }


}
