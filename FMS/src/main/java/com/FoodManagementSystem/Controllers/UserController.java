package com.FoodManagementSystem.Controllers;

import com.FoodManagementSystem.Models.User;
import com.FoodManagementSystem.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    //For Using in UI You Must First Create user Class Object
    @PostMapping("/register")
    public ResponseEntity<String> Register(@Valid @RequestBody User user){
       String registerResponse = userService.register(user);
       return ResponseEntity.ok(registerResponse);
    }
    @PostMapping("/login")
    public ResponseEntity<String> Login(@RequestParam String userName, @RequestParam String pass){
        String loginResponse = userService.login(userName,pass);
        if (loginResponse.contains("Invalid")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
        }
        return ResponseEntity.ok(loginResponse);
    }
    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Integer id){
        User userProfile = userService.getUserprofile(id);
        if (userProfile != null) {
            return ResponseEntity.ok(userProfile);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userProfile);
    }
    
    @GetMapping("/getAllDetails")
    public ResponseEntity<List<User>> getAll(){
        List<User> p1=userService.getAllDetails();
        return ResponseEntity.ok(p1);
    }
}
