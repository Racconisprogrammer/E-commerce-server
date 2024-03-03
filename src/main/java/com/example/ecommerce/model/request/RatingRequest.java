package com.example.ecommerce.model.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RatingRequest {

    private Long productId;

    private double rating;

}
