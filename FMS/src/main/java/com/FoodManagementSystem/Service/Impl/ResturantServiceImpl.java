package com.FoodManagementSystem.Service.Impl;

import com.FoodManagementSystem.Models.Resturant;
import com.FoodManagementSystem.Repositories.ResturantRepo;
import com.FoodManagementSystem.Service.ResturantService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ResturantServiceImpl implements ResturantService {

    @Autowired
    private ResturantRepo resturantRepo;

    private final String uploadDir = "uploads/";

    @Override
    public String createRestaurant(Resturant resturant, MultipartFile image) {

        try {

            if (resturantRepo.existsByRestaurantName(resturant.getRestaurantName())) {
                return "This Name Is Already Listed On This Platform";
            }

            // Create folder if not exists
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Save image
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.write(filePath, image.getBytes());

            // Save filename in DB
            resturant.setImageUrl(fileName);

            Resturant savedRestaurant = resturantRepo.save(resturant);

            return "Restaurant Creation Successful\nYour Restaurant ID Is ==> "
                    + savedRestaurant.getRid();

        } catch (Exception e) {
            return "Error Creating Restaurant";
        }
    }

    @Override
    public Resturant updateResturant(Integer restaurantId, Resturant restaurant, MultipartFile image) {

        Optional<Resturant> existingResturantOpt =
                Optional.ofNullable(resturantRepo.findByRid(restaurantId));

        if (existingResturantOpt.isPresent()) {

            Resturant existingResturant = existingResturantOpt.get();

            existingResturant.setRestaurantName(restaurant.getRestaurantName());
            existingResturant.setRestaurantAddress(restaurant.getRestaurantAddress());
            existingResturant.setRestaurantPhoneNo(restaurant.getRestaurantPhoneNo());
            existingResturant.setCusine(restaurant.getCusine());

            try {
                if (image != null && !image.isEmpty()) {

                    String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                    Path filePath = Paths.get(uploadDir + fileName);
                    Files.write(filePath, image.getBytes());
                    existingResturant.setImageUrl(fileName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return resturantRepo.save(existingResturant);
        }

        return null;
    }


    @Override
    @Transactional
    public String deleteResturant(Integer restaurantId) {

        if (resturantRepo.existsById(restaurantId)) {
            resturantRepo.deleteById(restaurantId);
            return "Restaurant Deleted Successfully";
        }

        return "Restaurant Not Found";
    }

    @Override
    public Resturant getRestaurantByRid(Integer restaurantId) {

        return resturantRepo.findById(restaurantId)
                .orElseThrow(() ->
                        new RuntimeException("Restaurant not found with id: " + restaurantId)
                );
    }

    @Override
    public List<Resturant> loadAllResturants() {
        return resturantRepo.findAll();
    }
}
