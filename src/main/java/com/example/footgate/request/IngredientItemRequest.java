package com.example.footgate.request;

import com.example.footgate.entities.IngredientCategory;
import com.example.footgate.entities.Restaurant;
import lombok.Data;

@Data
public class IngredientItemRequest {
    private String name;
    private Long restaurantId;
    private Long ingredientCategoryId;
}
