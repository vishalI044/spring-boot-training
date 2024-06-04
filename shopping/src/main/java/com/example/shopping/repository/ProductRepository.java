package com.example.shopping.repository;

import com.example.shopping.entity.table.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.price = :price, p.quantity = :quantity, p.updated_at = :updated_at WHERE p.name = :name")
    int updateProduct(
            @Param("price") Double price,
            @Param("quantity") Double quantity,
            @Param("updated_at") Timestamp updated_at,
            @Param("name") String name
    );

    @Transactional
    @Modifying
    @Query("DELETE FROM Product p WHERE p.name = :name")
    int deleteProduct(@Param("name") String name);
}
