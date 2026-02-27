package com.FoodManagementSystem.Repositories;

import com.FoodManagementSystem.Models.FoodItem;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface foodItemsRepo extends JpaRepository<FoodItem,Integer> {
    List<FoodItem> findByRestaurant_Rid(Integer rid);
    FoodItem findByFoodId(Integer foodId);
    void deleteByFoodId(Integer foodId);
}
