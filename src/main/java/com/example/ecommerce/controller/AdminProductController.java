package com.example.ecommerce.controller;


import com.example.ecommerce.controller.http.response.ApiResponse;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.exception.ProductException;
import com.example.ecommerce.model.request.AddItemRequest;
import com.example.ecommerce.model.request.CreateProductRequest;
import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;


    @PostMapping("/created/")
    public ResponseEntity<AddItemRequest> created(
            @ModelAttribute AddItemRequest addItemRequest,
            @RequestPart("file1") MultipartFile file1,
            @RequestPart("file2") MultipartFile file2,
            @RequestPart("file3") MultipartFile file3
    ) {

        System.out.println("Additem request " + addItemRequest);
        System.out.println("multipart file " + file1 + " " + " " + file2 + " " + " " + file3);

        return new ResponseEntity<>(addItemRequest, HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<ApiResponse> createProduct(
            @ModelAttribute CreateProductRequest request,
            @RequestPart("file1") MultipartFile file1,
            @RequestPart("file2") MultipartFile file2,
            @RequestPart("file3") MultipartFile file3,
            @RequestPart("file4") MultipartFile file4,
            @RequestPart("file5") MultipartFile file5
    ) throws IOException {

        productService.createProduct(request, file1, file2, file3, file4, file5);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(true);
        apiResponse.setMessage("Created product");

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
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
            @RequestBody CreateProductRequest[] request,
            @RequestPart("file1") MultipartFile file1,
            @RequestPart("file2") MultipartFile file2,
            @RequestPart("file3") MultipartFile file3,
            @RequestPart("file4") MultipartFile file4,
            @RequestPart("file5") MultipartFile file5
            ) throws IOException {

            for (CreateProductRequest productRequest : request) {
                productService.createProduct(productRequest, file1, file2, file3, file4, file5);
            }

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("product created successfully");
            apiResponse.setStatus(true);

            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
