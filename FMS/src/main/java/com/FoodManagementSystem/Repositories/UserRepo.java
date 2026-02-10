package com.FoodManagementSystem.Repositories;


import com.FoodManagementSystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
     User findByUid(Integer uid);
    User findByUsername(String username);
    User findByEmail(String email);


}
