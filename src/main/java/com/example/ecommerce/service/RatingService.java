package com.example.ecommerce.service;


import com.example.ecommerce.model.Rating;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.exception.ProductException;
import com.example.ecommerce.model.request.RatingRequest;

import java.util.List;

public interface RatingService {

    Rating createRating(RatingRequest ratingRequest, User user) throws ProductException;

    List<Rating> getProductsRating(Long productId);

}
