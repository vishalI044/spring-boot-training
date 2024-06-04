package com.example.ecommerce.repository;

import com.example.ecommerce.entity.ProductsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductsDetails, Long> {
}
