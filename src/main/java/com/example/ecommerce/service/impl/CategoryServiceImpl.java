package com.example.ecommerce.service.impl;


import com.example.ecommerce.controller.http.response.CategoryDto;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> allCategories = categoryRepository.findAll();
        Map<Long, CategoryDto> categoryMap = new HashMap<>();
        for (Category category : allCategories) {
            CategoryDto categoryDto = new CategoryDto(category.getId(), category.getName(), category.getLevel(), new ArrayList<>());
            categoryMap.put(category.getId(), categoryDto);
        }
        List<CategoryDto> rootCategories = new ArrayList<>();
        for (Category category : allCategories) {
            CategoryDto categoryDto = categoryMap.get(category.getId());
            if (category.getParentCategory()==null) {
                rootCategories.add(categoryDto);
            } else {
                CategoryDto parentCategoryDto = categoryMap.get(category.getParentCategory().getId());
                parentCategoryDto.getSubcategories().add(categoryDto);
            }

        }

        return rootCategories;
    }
}
