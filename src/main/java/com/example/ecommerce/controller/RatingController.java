package com.example.ecommerce.controller;

import com.example.ecommerce.model.Rating;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.exception.ProductException;
import com.example.ecommerce.model.exception.UserException;
import com.example.ecommerce.model.request.RatingRequest;
import com.example.ecommerce.service.RatingService;
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
@RequiredArgsConstructor
@RequestMapping("/api/ratings")
public class RatingController {

    private final UserService userService;
    private final RatingService ratingService;

    @PostMapping("/")
    public ResponseEntity<Rating> createRating(
            @RequestBody RatingRequest request,
            @RequestHeader("Authorization") String jwt
            ) throws UserException, ProductException {

        User user = userService.findUserProfileByJwt(jwt);
        Rating rating = ratingService.createRating(request, user);
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductsRating(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String jwt
            ) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Rating> ratings = ratingService.getProductsRating(productId);
        return new ResponseEntity<>(ratings, HttpStatus.CREATED);
    }
}
