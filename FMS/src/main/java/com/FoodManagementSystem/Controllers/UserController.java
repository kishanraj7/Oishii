package com.FoodManagementSystem.Controllers;

import com.FoodManagementSystem.Models.User;
import com.FoodManagementSystem.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> login(@RequestParam String userName,
                                   @RequestParam String pass) {

        User user = userService.login(userName, pass);

        if (user != null) {
            return ResponseEntity.ok(
                    Map.of(
                            "message", "Login Successful",
                            "userId", user.getUid(),
                            "username", user.getUsername()
                    )
            );
        } else {
            return ResponseEntity.status(401).body("Invalid Credentials");
        }
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
