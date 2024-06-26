package com.example.footgate.controller;

import com.example.footgate.entities.IngredientCategory;
import com.example.footgate.entities.IngredientsItem;
import com.example.footgate.request.IngredientCategoryRequest;
import com.example.footgate.request.IngredientItemRequest;
import com.example.footgate.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {
    @Autowired
    private IngredientsService  ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest req
                                                                       ) throws Exception {
        IngredientCategory item = ingredientsService.createIngredientCategory(req.getName(),req.getRestaurantId());

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientItemRequest req) throws Exception {
        IngredientsItem item = ingredientsService.createIngredientItem(req.getRestaurantId(),req.getName(),req.getIngredientCategoryId());

        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    @PutMapping("/{ingredientItemId}/stoke")
    public ResponseEntity<IngredientsItem> updateIngredientStock(@PathVariable Long ingredientItemId) throws Exception {
        IngredientsItem item = ingredientsService.updateStock(ingredientItemId);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(@PathVariable Long restaurantId) throws Exception {
        List<IngredientsItem> item = ingredientsService.findRestaurantIngredients(restaurantId);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    @GetMapping("/restaurant/{restaurantId}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(@PathVariable Long restaurantId) throws Exception {
        List<IngredientCategory> item = ingredientsService.findIngredientCategoryByRestaurantId(restaurantId);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

}
