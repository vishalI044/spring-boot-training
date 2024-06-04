package com.example.shopping.services;

import com.example.shopping.entity.table.Product;
import com.example.shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    long currentTimeMillis = System.currentTimeMillis();
    Timestamp currentTimestamp = new Timestamp(currentTimeMillis);

    public ResponseEntity<String> createProduct(Product productRequest) {
        Product productDetails = new Product();
        productDetails.setName(productRequest.getName());
        productDetails.setQuantity(productRequest.getQuantity());
        productDetails.setPrice(productRequest.getPrice());
        productDetails.setCreated_at(currentTimestamp);
        productRepository.save(productDetails);
        return ResponseEntity.status(HttpStatus.OK).body(productRequest.getName() + " is created");
    }

    public ResponseEntity<List<Product>> readProducts() {
        List<Product> productList = productRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    public ResponseEntity<Product> readProduct(String productName) {
        Product productDetails = productRepository.findByName(productName);
        return ResponseEntity.status(HttpStatus.OK).body(productDetails);
    }

    public ResponseEntity<String> updatedProductDetails(Product productRequest) {
        String productName = productRequest.getName();
        Double quantity = productRequest.getQuantity();
        Double price = productRequest.getPrice();
        int updated = productRepository.updateProduct(price, quantity, currentTimestamp, productName);
        if (updated == 1) {
            return ResponseEntity.status(HttpStatus.OK).body("product details updated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("product details not updated");
    }

    public ResponseEntity<String> deleteProduct(String productName) {
        int deleted = productRepository.deleteProduct(productName);
        if (deleted == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(productName + " is deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("unable to delete " + productName);
    }
}
