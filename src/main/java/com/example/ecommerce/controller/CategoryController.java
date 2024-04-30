package com.example.ecommerce.controller;


import com.example.ecommerce.controller.http.response.CategoryDto;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/api/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategory() {

        List<CategoryDto> category = categoryService.getAllCategory();

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

}
