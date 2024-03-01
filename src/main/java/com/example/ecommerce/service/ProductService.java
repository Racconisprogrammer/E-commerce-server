package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.exception.ProductException;
import com.example.ecommerce.model.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Product createProduct(CreateProductRequest productRequest);

    String deleteProduct(Long productId) throws ProductException;

    Product updateProduct(Long productId, Product req) throws ProductException;

    Product findProductById(Long productId) throws ProductException;

    List<Product> findProductByCategory(String category) throws ProductException;

    Page<Product> getAllProduct(String category, List<String> colors,
                                List<String> sizes, Integer minPrice,
                                Integer maxPrice, Integer minDiscount,
                                String sort, String stock,
                                Integer pageNumber, Integer pageSize) throws ProductException;

}
