package com.FoodManagementSystem.Models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="FoodItems")
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer foodId;
    private Integer rid;
    @NotBlank(message = "Name Can not Be Empty")
    private String name;
    private String description;
    @NotBlank(message = "Price Can't Be Empty")
    private double price;
    private boolean availability;
}
