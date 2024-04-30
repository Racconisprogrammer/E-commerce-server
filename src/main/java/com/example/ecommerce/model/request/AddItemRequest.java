package com.example.ecommerce.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddItemRequest {

    private Long productId;

    private int quantity;

    private Integer price;

}
