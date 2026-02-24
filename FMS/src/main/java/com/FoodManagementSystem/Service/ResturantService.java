package com.FoodManagementSystem.Service;
import com.FoodManagementSystem.Models.Resturant;
import com.FoodManagementSystem.Models.User;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ResturantService {

    String createRestaurant(Resturant resturant, MultipartFile image);

    Resturant updateResturant(Integer restaurantId, Resturant restaurant, MultipartFile image);

    String deleteResturant(Integer restaurantId);

    Resturant getRestaurantByRid(Integer restaurantId);

    List<Resturant> loadAllResturants();
}

