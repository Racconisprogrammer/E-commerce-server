package com.example.ecommerce.service;

import com.example.ecommerce.model.Review;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.exception.ProductException;
import com.example.ecommerce.model.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    Review createReview(ReviewRequest reviewRequest, User user) throws ProductException;

    List<Review> getAllReview(Long productId);

}
