package com.example.footgate.request;

import com.example.footgate.entities.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
}
