package com.example.shopping.controller;

import com.example.shopping.entity.table.Product;
import com.example.shopping.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setName("Test Product");
        product.setQuantity(10.0);
        product.setPrice(100.0);
        product.setCreated_at(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    void testCreateProduct() throws Exception {
        when(productService.createProduct(any(Product.class))).thenReturn(ResponseEntity.ok("Test Product is created"));

        mockMvc.perform(post("/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Product\",\"quantity\":10.0,\"price\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Test Product is created"));
    }

    @Test
    void testReadProducts() throws Exception {
        List<Product> products = Collections.singletonList(product);
        when(productService.readProducts()).thenReturn(ResponseEntity.ok(products));

        mockMvc.perform(get("/product/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].quantity").value(10.0))
                .andExpect(jsonPath("$[0].price").value(100.0));
    }

    @Test
    void testReadProduct() throws Exception {
        when(productService.readProduct(anyString())).thenReturn(ResponseEntity.ok(product));

        mockMvc.perform(get("/product/get/Test Product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.quantity").value(10.0))
                .andExpect(jsonPath("$.price").value(100.0));
    }

    @Test
    void testUpdatedProductDetails() throws Exception {
        when(productService.updatedProductDetails(any(Product.class))).thenReturn(ResponseEntity.ok("product details updated successfully"));

        mockMvc.perform(put("/product/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Product\",\"quantity\":20.0,\"price\":200.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string("product details updated successfully"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        when(productService.deleteProduct(anyString())).thenReturn(ResponseEntity.ok("Test Product is deleted"));

        mockMvc.perform(delete("/product/delete/Test Product"))
                .andExpect(status().isOk())
                .andExpect(content().string("Test Product is deleted"));
    }
}
