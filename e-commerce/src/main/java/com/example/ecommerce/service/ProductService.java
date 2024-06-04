package com.example.ecommerce.service;

import com.example.ecommerce.entity.ProductsDetails;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<String> addProducts(ProductsDetails productsDetailsRequest, String sessionId) throws Exception;
    ResponseEntity<String> updateProducts(ProductsDetails productsDetailsRequest, String sessionId) throws Exception;
    ResponseEntity<String> deleteProducts(ProductsDetails productsDetailsRequest, String sessionId) throws Exception;
}
