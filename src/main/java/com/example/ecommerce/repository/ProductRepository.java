package com.example.ecommerce.repository;

import com.example.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query(value = """
        SELECT p FROM Product p
        WHERE (p.category.name = :category OR :category = '')
        AND (COALESCE(:minPrice, -1) = -1 OR p.discountPrice BETWEEN :minPrice AND :maxPrice)
        AND (COALESCE(:minDiscount, -1) = -1 OR p.discountPercent >= :minDiscount)
        ORDER BY
        CASE WHEN :sort = 'price_low' THEN p.discountPrice END ASC,
        CASE WHEN :sort = 'price_high' THEN p.discountPrice END DESC
        """)
    List<Product> filterProducts(@Param("category") String category,
                                 @Param("minPrice") Integer minPrice,
                                 @Param("maxPrice") Integer maxPrice,
                                 @Param("minDiscount") Integer minDiscount,
                                 @Param("sort") String sort
                                 );

    @Query(value = """
        SELECT p FROM Product p
        join Category c on c.id = p.category.id
        where c.name = :categoryName
    """)
    List<Product> findProductsByCategoryName(@Param("categoryName") String categoryName);

}
