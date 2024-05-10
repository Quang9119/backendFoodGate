package com.example.footgate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;

//    @JsonIgnore
//    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
//    private List<IngredientsItem> ingredients = new ArrayList<>();
//    @JsonIgnore
//    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
//    private List<IngredientsItem> ingredients = new ArrayList<>();


}
