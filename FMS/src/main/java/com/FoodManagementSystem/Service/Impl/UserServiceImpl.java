package com.FoodManagementSystem.Service.Impl;
import com.FoodManagementSystem.Models.User;
import com.FoodManagementSystem.Repositories.UserRepo;
import com.FoodManagementSystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public String register(User user) {
        if(userRepo.findByUsername(user.getUsername()) == null && userRepo.findByEmail(user.getEmail()) == null){
            User savedUser=userRepo.save(user);
            return "User Registered Successfully"+"\n"+"Your User ID Is ==> "+savedUser.getUid();
        }
        else {
            return "This Email Or User Name Is Already Registered";
        }
    }

    @Override
    public User login(String username, String password) {

        User user = userRepo.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    @Override
    public User getUserprofile(Integer userId) {
        return userRepo.findById(userId).orElse(null);
    }
    public List<User> getAllDetails(){
        return userRepo.findAll();
    }
}
