package com.example.ecommerce.controller;


import com.example.ecommerce.model.ProductImage;
import com.example.ecommerce.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;


@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageRepository imageRepository;

    @GetMapping("/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable Long id) {
        ProductImage productImage = imageRepository.findById(id).orElse(null);
        return ResponseEntity.ok()
                .header("fileName", productImage.getOriginalFileName())
                .contentType(MediaType.valueOf(productImage.getContentType()))
                .contentLength(productImage.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(productImage.getBytes())));
    }

}
