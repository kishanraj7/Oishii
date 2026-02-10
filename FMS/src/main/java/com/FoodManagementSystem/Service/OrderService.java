package com.FoodManagementSystem.Service;


import com.FoodManagementSystem.Models.Order;

public interface OrderService {

    void placeOrder(Order order);
    void getOrdersByCustomerId(Integer customerId);
    void getOrdersByRestaurantId(Integer restaurantId);
    void updateOrderStatus(Integer orderId, String status);
}
