package com.FoodManagementSystem.Controllers;

import com.FoodManagementSystem.Models.Resturant;
import com.FoodManagementSystem.Service.ResturantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/restaurants")
public class ResturentController {
    @Autowired
    private ResturantService resturantService;
    @PostMapping("/createRestaurant")
    public ResponseEntity<String> createResturant(@Valid @RequestBody Resturant r){
        String createdRestaurant = resturantService.createRestaurant(r);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurant);
    }
    @PutMapping("/{resId}")
    public ResponseEntity<?> updateResturant(@PathVariable Integer resId, @Valid @RequestBody Resturant r) {
        Resturant updatedRestaurant = resturantService.updateResturant(resId, r);
        if (updatedRestaurant != null) {
            return ResponseEntity.ok(updatedRestaurant);
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
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Resturant>> getRestroByOwnerId(@PathVariable Integer ownerId) {
        List<Resturant> restaurants = resturantService.getResturantByOwnerId(ownerId);
        if (!restaurants.isEmpty()) {
            return ResponseEntity.ok(restaurants);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
