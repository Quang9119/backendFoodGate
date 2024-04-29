package com.example.footgate.service;

import com.example.footgate.entities.IngredientCategory;
import com.example.footgate.entities.IngredientsItem;

import java.util.List;

public interface IngredientsService {
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long categoryId) throws Exception;
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception;
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception;

    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long ingredientCategoryId) throws Exception;
    public IngredientsItem updateStock(Long ingredientId) throws Exception;
}
