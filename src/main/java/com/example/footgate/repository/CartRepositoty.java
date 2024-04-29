package com.example.footgate.repository;

import com.example.footgate.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepositoty extends JpaRepository<Cart, Long> {
    public Cart findByCustomerId(Long userId);


}
