package com.FoodManagementSystem.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "Restaurants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Resturant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer rid;

    private Integer ownerId;
    @PrePersist
    public void generateOwnerId() {
        if (this.ownerId == null) {
            this.ownerId = (int) (Math.random() * 100000);
        }
    }

    @NotBlank(message = "Restaurant name is mandatory")
    private String restaurantName;

    @NotBlank(message = "Address is mandatory")
    private String restaurantAddress;

    @NotBlank(message = "Phone number is mandatory")
    private String RestaurantPhoneNo;

    @NotBlank(message = "Cuisine type is mandatory")
    private String cusine;
}

