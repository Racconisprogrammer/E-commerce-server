package com.example.ecommerce.service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.exception.ProductException;
import com.example.ecommerce.model.request.AddItemRequest;

public interface CartService {

    Cart createCart(User user);

    String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    Cart findUserCart(Long userId);

}
