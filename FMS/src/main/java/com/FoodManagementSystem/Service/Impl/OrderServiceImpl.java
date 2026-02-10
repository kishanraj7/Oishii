package com.FoodManagementSystem.Service.Impl;

import com.FoodManagementSystem.Models.FoodItem;
import com.FoodManagementSystem.Models.Order;
import com.FoodManagementSystem.Repositories.OrderRepo;
import com.FoodManagementSystem.Repositories.ResturantRepo;
import com.FoodManagementSystem.Repositories.UserRepo;
import com.FoodManagementSystem.Repositories.foodItemsRepo;
import com.FoodManagementSystem.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private foodItemsRepo foodItems;
    @Autowired
    private UserRepo user;
    @Autowired
    private ResturantRepo restro;
    @Override
    public void placeOrder(Order order) {
        orderRepo.save(order);
        System.out.println("Order Placed SuccessFully");
    }
    public double calculateTotalPrice() {
        Order order = null;
        double total = 0;
        List<FoodItem> ItemList = foodItems.findAll();
        if (order.getFoodItems().equals(ItemList)) {
            for (FoodItem item : ItemList) {
                total += item.getPrice();
            }
            order.setTotalPrice(total);
        }
        return order.getTotalPrice();
    }

    @Override
    public void getOrdersByCustomerId(Integer customerId) {
        List<Order> orders = orderRepo.findAll();
        for (Order order : orders) {
            if (user.existsById(customerId)) {
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Food Items Ordered: " + order.getFoodItems());
                System.out.println("Restaurant ID: " + order.getRestaurantId());
                System.out.println("Grand Total: " + calculateTotalPrice());
                System.out.println("Order Status: " + order.getStatus());
                System.out.println("-------------------------------------");
            } else {
                System.out.println("No Orders Found for this Customer ID");
            }
        }
    }

    @Override
    public void getOrdersByRestaurantId(Integer restaurantId) {
        List<Order> orders = orderRepo.findAll();
        for (Order order : orders) {
            if (restro.existsByRid(restaurantId)) {
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Customer ID: " + order.getCustomerId());
                System.out.println("Food Items Ordered: " + order.getFoodItems());
                System.out.println("Grand Total: " + order.getTotalPrice());
                System.out.println("Order Status: " + order.getStatus());
                System.out.println("---------------------------");
            }
            else {
                System.out.println("No Orders Found For This Restaurant ID");
            }
        }
    }

    @Override
    public void updateOrderStatus(Integer orderId, String status) {
        if(orderRepo.existsByOrderId(orderId)){
            Order order = orderRepo.findById(orderId).orElse(null);
            if (order != null) {
                order.setStatus(status);
                orderRepo.save(order);
                System.out.println("Order status updated successfully");
            }
        } else {
            System.out.println("Order ID not found");
        }
    }
}
