package com.example.footgate.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class RestaurantDto {
    private String name;
    @Column(length = 1000)
    private List<String> image;

    private String description;
    private Long id;

    private boolean open;

}
