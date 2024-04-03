package com.example.ecommerce.controller;


import com.example.ecommerce.controller.http.response.ApiResponse;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.exception.ProductException;
import com.example.ecommerce.model.request.CreateProductRequest;
import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;


    @PostMapping("/")
    public ResponseEntity<Product> createProduct(
            @RequestBody CreateProductRequest request) {
        Product product = productService.createProduct(request);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(
            @PathVariable Long productId
    ) throws ProductException {

        productService.deleteProduct(productId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("product deleted successfully");
        apiResponse.setStatus(true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProduct() {
        List<Product> products = new ArrayList<>();

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProduct(
            @RequestBody Product req,
            @PathVariable Long productId
    ) throws ProductException {

        Product product = productService.updateProduct(productId, req);
        return new ResponseEntity<>(product, HttpStatus.CREATED);

    }

    @PostMapping("/creates")
    public ResponseEntity<ApiResponse> createMultipleProduct(
            @RequestBody CreateProductRequest[] request
            ) {

            for (CreateProductRequest productRequest : request) {
                productService.createProduct(productRequest);
            }

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("product created successfully");
            apiResponse.setStatus(true);

            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
