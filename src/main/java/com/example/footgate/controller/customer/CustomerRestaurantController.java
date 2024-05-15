package com.example.footgate.controller.customer;

import com.example.footgate.dto.RestaurantDto;
import com.example.footgate.entities.Restaurant;
import com.example.footgate.entities.User;
import com.example.footgate.request.CreateRestaurantRequest;
import com.example.footgate.response.ApiMessageDto;
import com.example.footgate.service.RestaurantService;
import com.example.footgate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class CustomerRestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestHeader("Authorization") String jwt,
                                                             @RequestParam String keyword) throws Exception {

        List<Restaurant> restaurants = restaurantService.searchRestaurants(keyword);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);

    }
    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurant(@RequestHeader("Authorization") String jwt) throws Exception {

        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);

    }
    @GetMapping("/favorites")
    public ResponseEntity<List<Restaurant>> getAllRestaurantFavorites(@RequestHeader("Authorization") String jwt) throws Exception {

        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(@RequestHeader("Authorization") String jwt,
                                                         @PathVariable Long id) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favorite")
    public ResponseEntity<Restaurant> addToFavorites(@RequestHeader("Authorization") String jwt,
                                                        @PathVariable Long id) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.addToFavourites(id,user);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
