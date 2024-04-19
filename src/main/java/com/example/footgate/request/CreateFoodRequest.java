package com.example.footgate.request;

import com.example.footgate.entities.Category;
import com.example.footgate.entities.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Long price;
    private Category category;
    private List<String> images;
    private Long restaurantId;
    private boolean vegetarian;
    private boolean sesional;
    private List<IngredientsItem> ingredients;
}
