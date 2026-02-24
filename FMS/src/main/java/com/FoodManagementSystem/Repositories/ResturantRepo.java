package com.FoodManagementSystem.Repositories;


import com.FoodManagementSystem.Models.Resturant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ResturantRepo extends JpaRepository<Resturant,Integer> {
    boolean existsByRestaurantName(String restaurantName);
    Resturant findByRid(Integer rid);
    Resturant deleteByRid(Integer rid);
    boolean existsByRid(Integer rid);
}
