package com.FoodManagementSystem.Controllers;

import com.FoodManagementSystem.Models.Resturant;
import com.FoodManagementSystem.Models.User;
import com.FoodManagementSystem.Service.ResturantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/restaurants")
public class ResturentController {
    @Autowired
    private ResturantService resturantService;
    @PostMapping(value = "/createRestaurant", consumes = "multipart/form-data")
    public ResponseEntity<?> createRestaurant(
            @RequestParam String restaurantName,
            @RequestParam String restaurantAddress,
            @RequestParam String restaurantPhoneNo,
            @RequestParam String cusine,
            @RequestParam MultipartFile image) {

        Resturant restaurant = new Resturant();
        restaurant.setRestaurantName(restaurantName);
        restaurant.setRestaurantAddress(restaurantAddress);
        restaurant.setRestaurantPhoneNo(restaurantPhoneNo);
        restaurant.setCusine(cusine);

        String response = resturantService.createRestaurant(restaurant, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/{resId}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateResturant(
            @PathVariable Integer resId,
            @RequestParam String restaurantName,
            @RequestParam String restaurantAddress,
            @RequestParam String restaurantPhoneNo,
            @RequestParam String cusine,
            @RequestParam(required = false) MultipartFile image) {

        Resturant restaurant = new Resturant();
        restaurant.setRestaurantName(restaurantName);
        restaurant.setRestaurantAddress(restaurantAddress);
        restaurant.setRestaurantPhoneNo(restaurantPhoneNo);
        restaurant.setCusine(cusine);
               Resturant updated = resturantService.updateResturant(resId, restaurant, image);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant Not Found");
        }
    }


    @DeleteMapping("/{resId}")
    public ResponseEntity<String> deleteResturant(@PathVariable Integer resId) {
        String response = resturantService.deleteResturant(resId);
        if (response.equals("Restaurant Deletion Successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @GetMapping("/find/{rid}")
    public ResponseEntity<Resturant> getRestaurantByRid(@PathVariable Integer rid) {
        Resturant restaurant = resturantService.getRestaurantByRid(rid);
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/load-restaurant")
    public ResponseEntity<List<Resturant>> loadAllRestaurant() {
        List<Resturant> p1=resturantService.loadAllResturants();
        return ResponseEntity.ok(p1);
    }
}
