package com.example.ecommerce.service;


import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.exception.CartItemException;
import com.example.ecommerce.model.exception.UserException;

public interface CartItemService {

    CartItem createCartItem(CartItem cartItem);

    CartItem updateCartItem(
            Long userId,
            Long id,
            CartItem cartItem
    ) throws CartItemException, UserException;

    CartItem isCartItemExist(
            Cart cart,
            Product product,
            String size,
            Long userId
    );

    void removeCartItem(
            Long userId,
            Long cartItemId
            ) throws CartItemException, UserException;

    CartItem findCartItemById(
            Long cartItemId
    ) throws CartItemException;

}
