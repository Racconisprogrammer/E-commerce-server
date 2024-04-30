package com.example.ecommerce.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    private String title;

    private String description;

    private int price;

    private int discountedPrice;

    private int discountedPercent;

    private int quantity;

    private String brand;

    private String color;

    private String highlights;

    private String details;

    private String topLevelCategory;

    private String secondLevelCategory;

    private String thirdLevelCategory;

}
