package com.example.ecommerce.controller;


import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.exception.ProductException;
import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(
            @RequestParam String category,
            @RequestParam List<String> color,
            @RequestParam List<String> size,
            @RequestParam Integer minPrice,
            @RequestParam Integer maxPrice,
            @RequestParam Integer minDiscount,
            @RequestParam String sort,
            @RequestParam String stock,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize
            ) throws ProductException {

        Page<Product> res = productService.getAllProduct(
                category,
                color,
                size,
                minPrice,
                maxPrice,
                minDiscount,
                sort,
                stock,
                pageNumber,
                pageSize
        );
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product> findProductByIdHandler(
            @PathVariable Long productId
    ) throws ProductException {

     Product product = productService.findProductById(productId);

     return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }
//
//    @GetMapping("/products/search")
//    public ResponseEntity<List<Product>> searchProductHandler(
//            @RequestParam String q
//    ) throws ProductException {
//
//        List<Product> products = productService.searchProduct(q);
//        List<Product> products = productService.findProductById(1L);
//
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }

}
