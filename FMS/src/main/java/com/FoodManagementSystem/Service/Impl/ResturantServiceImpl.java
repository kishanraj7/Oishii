package com.FoodManagementSystem.Service.Impl;


import com.FoodManagementSystem.Models.Resturant;
import com.FoodManagementSystem.Repositories.ResturantRepo;
import com.FoodManagementSystem.Service.ResturantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ResturantServiceImpl implements ResturantService {
    @Autowired
    private ResturantRepo resturantRepo;

    @Override
    public String createRestaurant(Resturant resturant) {
        if (resturantRepo.existsByRestaurantName((resturant.getRestaurantName()))){
            return "This Name Is Already Listed On This Platform";
        } else {
            Resturant savedRestaurant=resturantRepo.save(resturant);
            return "Restaurant Creation Successful"+"\n"+"Your Restaurant ID Is ==> "+savedRestaurant.getRid();
        }
    }
public Resturant updateResturant(Integer restaurantId, Resturant restaurant) {
    Optional<Resturant> existingResturantOpt = Optional.ofNullable(resturantRepo.findByRid(restaurantId));
    if (existingResturantOpt.isPresent()) {
        Resturant existingResturant = existingResturantOpt.get();
        existingResturant.setRestaurantName((restaurant.getRestaurantName()));
        existingResturant.setRestaurantAddress(restaurant.getRestaurantAddress());
        existingResturant.setRestaurantPhoneNo(restaurant.getRestaurantPhoneNo());
        existingResturant.setCusine(restaurant.getCusine());
        return resturantRepo.save(existingResturant);
    } else {
        return null;
    }
}

    @Override
    public String deleteResturant(Integer restaurantId) {
        if (resturantRepo.existsByRid(restaurantId)){
        resturantRepo.deleteByRid(restaurantId);
        return "Restaurant Deletion Successfully";
    }  else {
            return "Restaurant Not Found";
        }
    }

    @Override
    public List<Resturant> getResturantByOwnerId(Integer ownerId) {
        return resturantRepo.findByOwnerId(ownerId);
    }

}

