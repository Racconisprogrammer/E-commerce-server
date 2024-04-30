package com.example.ecommerce.repository;


import com.example.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);


    @Query(value = """
            SELECT c FROM Category c
            WHERE c.name = :name AND
            c.parentCategory.name =:parentCategoryName
            """)
    Category findByNameAndParent(@Param("name") String name,
                                 @Param("parentCategoryName") String parentCategoryName
                                 );
}
