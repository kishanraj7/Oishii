package com.FoodManagementSystem.Controllers;

import com.FoodManagementSystem.Models.Order;
import com.FoodManagementSystem.Models.OrderItem;
import com.FoodManagementSystem.Models.OrderStatus;
import com.FoodManagementSystem.Service.Impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    // -----------------------------
    // Place an order
    // -----------------------------
    @PostMapping("/place-order")
    public ResponseEntity<Order> placeOrder(
            @RequestParam Integer customerId,
            @RequestParam Integer restaurantId,
            @RequestParam List<Integer> foodIds,
            @RequestParam List<Integer> quantities,
            @RequestParam String deliveryAddress
    ) {
        return ResponseEntity.ok(
                orderService.placeOrder(
                        customerId,
                        restaurantId,
                        foodIds,
                        quantities,
                        deliveryAddress
                )
        );
    }

    // -----------------------------
    // Get orders by customer
    // -----------------------------
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable Integer customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }

    // -----------------------------
    // Get orders by restaurant
    // -----------------------------
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Order>> getOrdersByRestaurant(@PathVariable Integer restaurantId) {
        return ResponseEntity.ok(orderService.getOrdersByRestaurantId(restaurantId));
    }

    // -----------------------------
    // Update order status
    // -----------------------------
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Integer orderId,
            @RequestParam OrderStatus status
    ) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }

}