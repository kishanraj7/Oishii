package com.FoodManagementSystem.Controllers;


import com.FoodManagementSystem.Models.Order;
import com.FoodManagementSystem.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/orders")
public class orderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/place-order")
    public void place_Order(@RequestBody Order order){
        orderService.placeOrder(order);
    }
    @GetMapping("/customer/{cusId}")
    public void checkStatus(@PathVariable Integer cusId){
        orderService.getOrdersByCustomerId(cusId);
    }
    //For Restaurant
    @GetMapping("/restaurant/{resId}")
    public void viewOrder(@RequestBody Integer resid){
        orderService.getOrdersByRestaurantId(resid);
    }
    @PutMapping("/{oId}/status")
    public void updateOrderStatus(@PathVariable Integer oId,@RequestParam String status){
        orderService.updateOrderStatus(oId,status);
    }

}
