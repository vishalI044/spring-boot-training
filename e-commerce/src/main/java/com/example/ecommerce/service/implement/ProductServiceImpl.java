package com.example.ecommerce.service.implement;

import com.example.ecommerce.entity.ProductsDetails;
import com.example.ecommerce.repository.ProductDetailsRepository;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.utils.CommonHelper;
import com.example.ecommerce.utils.EncryptDecrypt;
import com.example.ecommerce.utils.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDetailsRepository productDetailsRepository;

    @Autowired
    Constants constants;

    @Autowired
    CommonHelper commonHelper;

    @Override
    public ResponseEntity<String> addProducts(ProductsDetails productsDetailsRequest, String sessionId) throws Exception {
        boolean isAdmin = adminValidation(sessionId);
        if (isAdmin){
            productsDetailsRequest.setCreated_at(Timestamp.from(Instant.now()));
            productDetailsRepository.save(productsDetailsRequest);
            return ResponseEntity.status(HttpStatus.OK).body(productsDetailsRequest.getName() + " is added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only Admin is allowed to add product");
        }
    }

    @Override
    public ResponseEntity<String> updateProducts(ProductsDetails productsDetailsRequest, String sessionId) throws Exception {
        boolean isAdmin = adminValidation(sessionId);

        if (isAdmin){
            productsDetailsRequest.setUpdated_at(Timestamp.from(Instant.now()));
            productDetailsRepository.save(productsDetailsRequest);
            return ResponseEntity.status(HttpStatus.OK).body(productsDetailsRequest.getName() + " is updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only Admin is allowed to update product");
        }
    }

    @Override
    public ResponseEntity<String> deleteProducts(ProductsDetails productsDetailsRequest, String sessionId) throws Exception {
        boolean isAdmin = adminValidation(sessionId);
        if (isAdmin) {
            productDetailsRepository.delete(productsDetailsRequest);
            return ResponseEntity.status(HttpStatus.OK).body(productsDetailsRequest.getName() + " is deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only Admin is allowed to delete product");
        }
    }

    public boolean adminValidation(String sessionId) throws Exception {
        String[] sessionDetails = EncryptDecrypt.decrypt(sessionId).split("\\|");
        String redisSession = commonHelper.getRedisDetails(constants.REDIS_SESSION_KEY, sessionDetails[0]);
        String userName = (EncryptDecrypt.decrypt(redisSession).split("\\|"))[1];

        return constants.ADMIN.equalsIgnoreCase(userName);
    }
}
