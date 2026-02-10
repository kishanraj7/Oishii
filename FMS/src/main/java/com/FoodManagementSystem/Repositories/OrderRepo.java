package com.FoodManagementSystem.Repositories;

import com.FoodManagementSystem.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order,Integer> {
    boolean existsByOrderId(Integer orderId);

}
