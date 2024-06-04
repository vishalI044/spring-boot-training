package com.example.shopping.controller;

import com.example.shopping.entity.table.Product;
import com.example.shopping.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping(path = "/create")
    public ResponseEntity<String> createProduct(@RequestBody Product productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Product>> readProducts() {
        return productService.readProducts();
    }

    @GetMapping(path = "/get/{name}")
    public ResponseEntity<Product> readProduct(@PathVariable("name") String productName) {
        return productService.readProduct(productName);
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<String> updatedProductDetails(@RequestBody Product productRequest) {
        return productService.updatedProductDetails(productRequest);
    }

    @DeleteMapping(path = "/delete/{name}")
    public ResponseEntity<String> deleteProduct(@PathVariable("name") String productName) {
        return productService.deleteProduct(productName);
    }
}
