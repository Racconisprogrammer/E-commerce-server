package com.example.ecommerce.repository;


import com.example.ecommerce.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ProductImage, Long> {
}
