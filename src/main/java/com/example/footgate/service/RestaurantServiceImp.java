package com.example.footgate.service;

import com.example.footgate.dto.RestaurantDto;
import com.example.footgate.entities.Address;
import com.example.footgate.entities.Restaurant;
import com.example.footgate.entities.User;
import com.example.footgate.repository.AddressRepository;
import com.example.footgate.repository.RestaurantRepository;
import com.example.footgate.repository.UserRepository;
import com.example.footgate.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) throws Exception {
        Address address = addressRepository.save(req.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception {


        Restaurant restaurant = findRestaurantById(restaurantId);
        if(restaurant.getCuisineType() !=null) {
            restaurant.setCuisineType(updateRestaurant.getCuisineType());
        }
        if(restaurant.getDescription() !=null) {
            restaurant.setDescription(updateRestaurant.getDescription());
        }
        if(restaurant.getName()!=null) {
            restaurant.setName(updateRestaurant.getName());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {


        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurants(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(restaurantId);

        if(opt.isEmpty()) {
            throw new Exception("Restaurant not found with id: " + restaurantId);
        }

        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if(restaurant ==null) {
            throw new Exception("Restaurant not found with owner id: " + userId);
        }


        return restaurant;
    }

    @Override
    public Restaurant addToFavourites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        boolean isFavourite = false;
        List<Restaurant> favourites = user.getFavorites();
        for(Restaurant favorite: favourites) {
            if(favorite.getId().equals(restaurantId)) {
                isFavourite = true;
                break;
            }
        }
        if(isFavourite) {
            favourites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        }
        else {
            favourites.add(restaurant);
        }

        userRepository.save(user);
        return restaurant;
    }


    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());

        return restaurantRepository.save(restaurant);
    }
}
