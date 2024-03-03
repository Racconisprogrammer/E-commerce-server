package com.example.ecommerce.repository;


import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query(value = """
        SELECT ci FROM CartItem ci
        WHERE ci.cart=:cart
        AND ci.product =:product
        AND ci.size =:size
        AND ci.userId =:userId
    """, nativeQuery = true)
    CartItem isCartItemExist(
            @Param("cart") Cart cart,
            @Param("product") Product product,
            @Param("size") String size,
            @Param("userId") Long userId
            );
}
