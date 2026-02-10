package com.FoodManagementSystem.Repositories;

import com.FoodManagementSystem.Models.FoodItem;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface foodItemsRepo extends JpaRepository<FoodItem,Integer> {
//    void findByResturantId(String resturantId);
    FoodItem findByFoodId(Integer foodId);
    FoodItem deleteByFoodId(Integer foodId);
}
