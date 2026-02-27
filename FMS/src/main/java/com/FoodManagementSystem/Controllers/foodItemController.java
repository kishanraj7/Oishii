package com.FoodManagementSystem.Controllers;

import com.FoodManagementSystem.Models.FoodItem;
import com.FoodManagementSystem.Service.foodItemService;
import org.apache.logging.log4j.util.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/food-items")
public class foodItemController {
    @Autowired
    private foodItemService foodItemService;

    @PostMapping(value = "/restaurant/{resid}", consumes = "multipart/form-data")
    public ResponseEntity<String> addItem(
            @PathVariable Integer resid,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam boolean availability,
            @RequestParam(required = false) MultipartFile image) {

        // Build entity
        FoodItem foodItem = new FoodItem();
        foodItem.setName(name);
        foodItem.setDescription(description);
        foodItem.setPrice(price);
        foodItem.setAvailability(availability);

        String response = foodItemService.addFoodItem(resid, foodItem, image);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping(value = "/{fid}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateItem(
            @PathVariable Integer fid,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam boolean availability,
            @RequestParam(required = false) MultipartFile image) {

        try {
            FoodItem foodItem = new FoodItem();
            foodItem.setName(name);
            foodItem.setDescription(description);
            foodItem.setPrice(price);
            foodItem.setAvailability(availability);

            FoodItem updated = foodItemService.updateFoodItem(fid, foodItem, image);

            return ResponseEntity.ok(updated);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{fid}")
    public ResponseEntity<String> deleteItem(@PathVariable Integer fid) {

        String response = foodItemService.deleteFoodItem(fid);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/restaurant/{resId}")
    public ResponseEntity<List<FoodItem>> loadMenu(@PathVariable Integer resId) {

        java.util.List<FoodItem> foodItems =
                foodItemService.getFoodItemsByRestaurantId(resId);

        return ResponseEntity.ok(foodItems);
    }


}