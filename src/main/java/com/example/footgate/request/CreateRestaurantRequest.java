package com.example.footgate.request;

import com.example.footgate.entities.Address;
import com.example.footgate.entities.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantRequest {

    private Long id;
    private String name;
    private Address address;
    private String description;
    private String cuisineType;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;
}
