package com.FoodManagementSystem.Service;

import com.FoodManagementSystem.Models.Order;
import com.FoodManagementSystem.Models.OrderItem;
import com.FoodManagementSystem.Models.OrderStatus;

import java.util.List;

public interface OrderService {

    Order placeOrder(Integer customerId,
                     Integer restaurantId,
                     List<Integer> foodIds,
                     List<Integer> quantities,
                     String deliveryAddress);

    List<Order> getOrdersByCustomerId(Integer customerId);

    List<Order> getOrdersByRestaurantId(Integer restaurantId);

    Order updateOrderStatus(Integer orderId, OrderStatus status);
}