package com.example.footgate.controller;

import com.example.footgate.entities.Food;
import com.example.footgate.entities.Restaurant;
import com.example.footgate.entities.User;
import com.example.footgate.request.CreateFoodRequest;
import com.example.footgate.response.MessageResponse;
import com.example.footgate.service.FoodService;
import com.example.footgate.service.RestaurantService;
import com.example.footgate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> creatFood(@RequestBody CreateFoodRequest req,
                                          @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Food food = foodService.createFood(req, req.getCategory(), restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }
    @PutMapping("/{foodId}")
    public ResponseEntity<Food> updateFoodAvaibilityStatus(@PathVariable Long foodId,
                                                            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailibility(foodId);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }
    @DeleteMapping("/{foodId}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long foodId,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(foodId);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Food deleted successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

}
