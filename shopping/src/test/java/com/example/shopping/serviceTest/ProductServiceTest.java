package com.example.shopping.serviceTest;

import com.example.shopping.entity.table.Product;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        product = new Product();
        product.setName("Test Product");
        product.setQuantity(10.0);
        product.setPrice(100.0);
        product.setCreated_at(currentTimestamp);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.save(product)).thenReturn(product);

        ResponseEntity<String> response = productService.createProduct(product);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Product is created", response.getBody());
    }

    @Test
    void testReadProducts() {
        List<Product> productList = Collections.singletonList(product);

        when(productRepository.findAll()).thenReturn(productList);

        ResponseEntity<List<Product>> response = productService.readProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productList, response.getBody());
    }

    @Test
    void testReadProduct() {
        when(productRepository.findByName(anyString())).thenReturn(product);

        ResponseEntity<Product> response = productService.readProduct("Test Product");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    void testUpdatedProductDetails() {
        when(productRepository.updateProduct(anyDouble(), anyDouble(), any(Timestamp.class), anyString())).thenReturn(1);

        Product updatedProduct = new Product();
        updatedProduct.setName("Test Product");
        updatedProduct.setQuantity(20.0);
        updatedProduct.setPrice(200.0);

        ResponseEntity<String> response = productService.updatedProductDetails(updatedProduct);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("product details updated successfully", response.getBody());
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.deleteProduct(anyString())).thenReturn(1);

        ResponseEntity<String> response = productService.deleteProduct("Test Product");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Product is deleted", response.getBody());
    }
}
