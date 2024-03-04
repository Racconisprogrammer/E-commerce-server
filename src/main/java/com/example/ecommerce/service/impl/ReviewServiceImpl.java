package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Review;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.exception.ProductException;
import com.example.ecommerce.model.request.ReviewRequest;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.ReviewRepository;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;


    @Override
    public Review createReview(ReviewRequest reviewRequest, User user) throws ProductException {
        Product product = productService.findProductById(reviewRequest.getProductId());

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(reviewRequest.getReview());
        review.setCreateAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
}
