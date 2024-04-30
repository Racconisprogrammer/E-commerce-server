package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.exception.ProductException;
import com.example.ecommerce.model.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Product createProduct(
            CreateProductRequest productRequest,
            MultipartFile file1,
            MultipartFile file2,
            MultipartFile file3,
            MultipartFile file4,
            MultipartFile file5
                          ) throws IOException;

    String deleteProduct(Long productId) throws ProductException;

    Product updateProduct(Long productId, Product req) throws ProductException;

    Product findProductById(Long productId) throws ProductException;

    List<Product> findProductByCategory(String categoryName) throws ProductException;

    List<Product> getAllProducts();

    Page<Product> getAllProduct(String category, List<String> colors,
                                Integer minPrice,
                                Integer maxPrice, Integer minDiscount,
                                String sort, String stock,
                                Integer pageNumber, Integer pageSize) throws ProductException;

}
