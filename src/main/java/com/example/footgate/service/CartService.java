package com.example.footgate.service;

import com.example.footgate.entities.Cart;
import com.example.footgate.entities.CartItem;
import com.example.footgate.request.AddCartItemRequest;

public interface CartService {
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception;
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    public Cart removeItemFromCart(Long cartItemId,String jwt) throws Exception;
    public Long caculateCartTotals(Cart cart) throws Exception;

    public Cart findCartByCartId(Long cartId) throws Exception;

    public Cart findCartByUserId(Long userId) throws Exception;

    public Cart clearCart(Long userId) throws Exception;
}
