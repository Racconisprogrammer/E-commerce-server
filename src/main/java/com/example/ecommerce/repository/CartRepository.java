package com.example.ecommerce.repository;


import com.example.ecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = """
        SELECT c FROM Cart c
        WHERE c.user.id =:userId
    """)
    Cart findByUserId(@Param("userId") Long userId);



}
