package com.FoodManagementSystem.Service.Impl;

import com.FoodManagementSystem.Models.*;
import com.FoodManagementSystem.Repositories.OrderRepo;
import com.FoodManagementSystem.Repositories.ResturantRepo;
import com.FoodManagementSystem.Repositories.UserRepo;
import com.FoodManagementSystem.Repositories.foodItemsRepo;
import com.FoodManagementSystem.Service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private foodItemsRepo foodItemRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ResturantRepo restroRepo;

    @Transactional
    @Override
    public Order placeOrder(
            Integer customerId,
            Integer restaurantId,
            List<Integer> foodIds,
            List<Integer> quantities,
            String deliveryAddress
    ) {

        if (foodIds.size() != quantities.size()) {
            throw new RuntimeException("Food IDs and quantities size mismatch");
        }

        User customer = userRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Resturant restaurant = restroRepo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(deliveryAddress);
        order.setStatus(OrderStatus.RECEIVED);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setOrderTime(LocalDateTime.now());

        double totalPrice = 0;

        for (int i = 0; i < foodIds.size(); i++) {

            FoodItem food = foodItemRepo.findById(foodIds.get(i))
                    .orElseThrow(() -> new RuntimeException("Food not found"));

            int quantity = quantities.get(i);

            OrderItem orderItem = new OrderItem();
            orderItem.setFoodItem(food);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(food.getPrice() * quantity);

            totalPrice += orderItem.getPrice();

            // ✅ Use helper method (VERY IMPORTANT)
            order.addOrderItem(orderItem);
        }

        order.setTotalPrice(totalPrice);

        return orderRepo.save(order);
    }

    @Override
    public List<Order> getOrdersByCustomerId(Integer customerId) {
        User customer = userRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return orderRepo.findByCustomer(customer);
    }

    @Override
    public List<Order> getOrdersByRestaurantId(Integer restaurantId) {
        Resturant restaurant = restroRepo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        return orderRepo.findByRestaurant(restaurant);
    }


    @Override
    public Order updateOrderStatus(Integer orderId, OrderStatus status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepo.save(order);
    }
}