package com.FoodManagementSystem.Repositories;


import com.FoodManagementSystem.Models.Order;
import com.FoodManagementSystem.Models.OrderStatus;
import com.FoodManagementSystem.Models.Resturant;
import com.FoodManagementSystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
     User findByUid(Integer uid);
    User findByUsername(String username);
    User findByEmail(String email);



}
