package com.FoodManagementSystem.Service.Impl;
import com.FoodManagementSystem.Models.FoodItem;
import com.FoodManagementSystem.Models.Resturant;
import com.FoodManagementSystem.Repositories.ResturantRepo;
import com.FoodManagementSystem.Repositories.foodItemsRepo;
import com.FoodManagementSystem.Service.foodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class foodItemServiceImpl implements foodItemService {

    @Autowired
    private foodItemsRepo foodItemRepo;

    @Autowired
    private ResturantRepo resturantRepo;

    private final String uploadDir = "uploads/";

    @Override
    public String addFoodItem(Integer restaurantId, FoodItem foodItem, MultipartFile image) {

        Resturant restaurant = resturantRepo.findById(restaurantId)
                .orElseThrow(() ->
                        new RuntimeException("Restaurant not found with id: " + restaurantId)
                );

        try {

            // Save image if exists
            if (image != null && !image.isEmpty()) {

                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.write(filePath, image.getBytes());

                foodItem.setFoodImage(fileName);
            }

            // 🔥 IMPORTANT — Link food item to restaurant
            foodItem.setRestaurant(restaurant);

            foodItemRepo.save(foodItem);

            return "Food Item Added Successfully";

        } catch (Exception e) {
            return "Error Adding Food Item";
        }
    }

    @Override
    public FoodItem updateFoodItem(Integer foodItemId, FoodItem updatedFoodItem, MultipartFile image) {

        FoodItem existingFoodItem = foodItemRepo.findByFoodId(foodItemId);

        if (existingFoodItem == null) {
            throw new RuntimeException("Food item not found with id: " + foodItemId);
        }

        existingFoodItem.setName(updatedFoodItem.getName());
        existingFoodItem.setDescription(updatedFoodItem.getDescription());
        existingFoodItem.setPrice(updatedFoodItem.getPrice());
        existingFoodItem.setAvailability(updatedFoodItem.isAvailability());

        try {
            if (image != null && !image.isEmpty()) {

                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.write(filePath, image.getBytes());

                existingFoodItem.setFoodImage(fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return foodItemRepo.save(existingFoodItem);
    }

    @Override
    public String deleteFoodItem(Integer foodItemId) {

        FoodItem existingItem = foodItemRepo.findByFoodId(foodItemId);

        if (existingItem == null) {
            return "Food Item Not Found";
        }

        foodItemRepo.delete(existingItem);
        return "Food Item Deleted Successfully";
    }

    @Override
    public List<FoodItem> getFoodItemsByRestaurantId(Integer restaurantId) {

        if (!resturantRepo.existsById(restaurantId)) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }

        return foodItemRepo.findByRestaurant_Rid(restaurantId);
    }
}
