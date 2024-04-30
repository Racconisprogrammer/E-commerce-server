package com.example.ecommerce.service;


import com.example.ecommerce.controller.http.response.CategoryDto;


import java.util.List;


public interface CategoryService {

    List<CategoryDto> getAllCategory();
}
