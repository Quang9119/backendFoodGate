package com.example.footgate.service;

import com.example.footgate.entities.Category;
import com.example.footgate.entities.Food;
import com.example.footgate.entities.Restaurant;
import com.example.footgate.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    public Food deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantFoods(Long restaurantId,
                                         boolean isVegetarian,
                                         boolean isNonveg,
                                         boolean isSeasonal,
                                         String foodCategory) throws Exception;

    public List<Food> searchFood(String keyword) throws Exception;

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailibility(Long foodId) throws Exception;



}
