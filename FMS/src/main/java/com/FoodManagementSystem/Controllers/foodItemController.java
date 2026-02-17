package com.FoodManagementSystem.Controllers;

import com.FoodManagementSystem.Models.FoodItem;
import com.FoodManagementSystem.Service.foodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/food-items")
public class foodItemController {
    @Autowired
    private foodItemService foodItemService;
    @PostMapping("/restaurant/{resid}")
    public void addItem(@PathVariable Integer resid, @RequestBody FoodItem foodItem){
        foodItemService.addFoodItem(resid,foodItem);
    }

    @PutMapping("/{fid}")
    public void updateItem(@PathVariable Integer fid,@RequestBody FoodItem foodItem){
        foodItemService.updateFoodItem(fid,foodItem);
    }

    @DeleteMapping("/{fid}")
    public void deleteItem(@PathVariable Integer Fid){
        foodItemService.deleteFoodItem(Fid);
    }


    //To Resturant And Customer
    @GetMapping("/restaurant/{resId}")
    public void loadMenu(@PathVariable Integer resId){
        foodItemService.getFoodItemsByRestaurantId(resId);
    }

}
