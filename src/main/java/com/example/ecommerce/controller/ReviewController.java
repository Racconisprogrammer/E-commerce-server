package com.example.ecommerce.controller;


import com.example.ecommerce.model.Review;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.exception.ProductException;
import com.example.ecommerce.model.exception.UserException;
import com.example.ecommerce.model.request.ReviewRequest;
import com.example.ecommerce.service.ReviewService;
import com.example.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(
            @RequestBody ReviewRequest request,
            @RequestHeader("Authorization") String jwt
            ) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);

        Review review = reviewService.createReview(request, user);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductsReview(
            @PathVariable Long productId
    ) throws UserException, ProductException {

        List<Review> reviews = reviewService.getAllReview(productId);

        return new ResponseEntity<>(reviews, HttpStatus.ACCEPTED);
    }


}
