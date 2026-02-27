package com.FoodManagementSystem.Repositories;

import com.FoodManagementSystem.Models.Order;
import com.FoodManagementSystem.Models.OrderStatus;
import com.FoodManagementSystem.Models.Resturant;
import com.FoodManagementSystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Integer> {
    boolean existsByOrderId(Integer orderId);
    List<Order> findByCustomer(User customer);
    List<Order> findByRestaurant(Resturant restaurant);
    List<Order> findByRestaurantAndStatus(Resturant restaurant, OrderStatus status);

}
