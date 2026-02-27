package com.FoodManagementSystem.Service;

import com.FoodManagementSystem.Models.User;

import java.util.List;

public interface UserService {
    String register(User user);
    User login(String userName, String password);
    User getUserprofile(Integer userId);
    List<User> getAllDetails();
}
