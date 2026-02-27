package com.FoodManagementSystem.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    // 🔹 Customer who placed order
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    // 🔹 Restaurant receiving order
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Resturant restaurant;

    // 🔹 Order Items
    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private String deliveryAddress;

    private LocalDateTime orderTime;

    private LocalDateTime deliveredTime;
    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);   // set parent reference
    }

    public void removeOrderItem(OrderItem item) {
        orderItems.remove(item);
        item.setOrder(null);
    }
}