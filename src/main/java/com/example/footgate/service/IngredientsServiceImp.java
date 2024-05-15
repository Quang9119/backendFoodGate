package com.example.footgate.service;

import com.example.footgate.entities.IngredientCategory;
import com.example.footgate.entities.IngredientsItem;
import com.example.footgate.entities.Restaurant;
import com.example.footgate.repository.IngredientCategoryRepository;
import com.example.footgate.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImp implements IngredientsService{

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);
        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long ingredientCategoryId) throws Exception {
        Optional<IngredientCategory> ingredientCategory = ingredientCategoryRepository.findById(ingredientCategoryId);
        if(ingredientCategory.isEmpty()) {
            throw new Exception("Ingredient Category not found");
        }

        return ingredientCategory.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception {
        restaurantService.findRestaurantById(restaurantId);
        return ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception {
        restaurantService.findRestaurantById(restaurantId);
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long ingredientCategoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = findIngredientCategoryById(ingredientCategoryId);
        IngredientsItem ingredientsItem = new IngredientsItem();
        ingredientsItem.setName(ingredientName);
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setCategory(ingredientCategory);
        ingredientsItem.setInStoke(true);
        IngredientsItem item =  ingredientItemRepository.save(ingredientsItem);
//        ingredientCategory.getIngredients().add(item);
        return item;
    }

    @Override
    public IngredientsItem updateStock(Long ingredientId) throws Exception {
        Optional<IngredientsItem> ingredientsItem = ingredientItemRepository.findById(ingredientId);
        if(ingredientsItem.isEmpty()) {
            throw new Exception("Ingredient not found");
        }
        IngredientsItem item = ingredientsItem.get();
        item.setInStoke(!item.isInStoke());
        return ingredientItemRepository.save(item);
    }
}
