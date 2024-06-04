package com.example.ecommerce.service.implement;

import com.example.ecommerce.entity.Orders;
import com.example.ecommerce.entity.ProductsDetails;
import com.example.ecommerce.repository.OrdersRepository;
import com.example.ecommerce.repository.ProductDetailsRepository;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.utils.CommonHelper;
import com.example.ecommerce.utils.EncryptDecrypt;
import com.example.ecommerce.utils.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    ProductDetailsRepository productDetailsRepository;

    @Autowired
    CommonHelper commonHelper;

    @Autowired
    Constants constants;

    public ResponseEntity<String> placeOrder(Orders ordersRequest, String session) throws Exception {
        String sessionUserName = EncryptDecrypt.decrypt(session).split("\\|")[1];
        String redisSession = commonHelper.getRedisDetails(constants.REDIS_SESSION_KEY, sessionUserName);
        if (redisSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid session");
        }

        if (ordersRequest.status.equalsIgnoreCase(constants.ORDER_STATUS_EXECUTE)) {
            ProductsDetails productsDetails =
                    productDetailsRepository.findById(
                            ordersRequest.orderDetails.productId
                    ).orElseThrow(
                            () -> new RuntimeException("Product not found with id: " + ordersRequest.orderDetails.productId));
            productsDetails.setQuantity(productsDetails.getQuantity() - ordersRequest.orderDetails.productQuantity);
            productDetailsRepository.save(productsDetails);
        }
        ordersRepository.save(ordersRequest);
        return ResponseEntity.status(HttpStatus.OK).body("order saved");
    }

    public ResponseEntity<List<Orders>> getOrders(Long userId, String session) throws Exception {
        String sessionUserName = EncryptDecrypt.decrypt(session).split("\\|")[1];
        String redisSession = commonHelper.getRedisDetails(constants.REDIS_SESSION_KEY, sessionUserName);
        if (redisSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(ordersRepository.findByUserId(userId));
    }

    public ResponseEntity<Orders> getOrder(Long id, String session) throws Exception {
        String sessionUserName = EncryptDecrypt.decrypt(session).split("\\|")[1];
        String redisSession = commonHelper.getRedisDetails(constants.REDIS_SESSION_KEY, sessionUserName);
        if (redisSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Orders orderDetails = ordersRepository.findById(id).orElseThrow(
                () -> new RuntimeException("order not found with id: " + id));
        return ResponseEntity.status(HttpStatus.OK).body(orderDetails);
    }

    public ResponseEntity<String> modifyOrder(Orders ordersRequest, String session) throws Exception {
        String sessionUserName = EncryptDecrypt.decrypt(session).split("\\|")[1];
        String redisSession = commonHelper.getRedisDetails(constants.REDIS_SESSION_KEY, sessionUserName);
        if (redisSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (ordersRequest.status.equalsIgnoreCase(constants.ORDER_STATUS_EXECUTE)) {
            ProductsDetails productsDetails =
                    productDetailsRepository.findById(
                            ordersRequest.orderDetails.productId
                    ).orElseThrow(
                            () -> new RuntimeException("Product not found with id: " + ordersRequest.orderDetails.productId));
            productsDetails.setQuantity(productsDetails.getQuantity() - ordersRequest.orderDetails.productQuantity);
            productDetailsRepository.save(productsDetails);
        }
        ordersRepository.save(ordersRequest);
        return ResponseEntity.status(HttpStatus.OK).body("order updated successfully");
    }

    public ResponseEntity<String> deleteOrder(Long id, String session) throws Exception {
        String sessionUserName = EncryptDecrypt.decrypt(session).split("\\|")[1];
        String redisSession = commonHelper.getRedisDetails(constants.REDIS_SESSION_KEY, sessionUserName);
        if (redisSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ordersRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("order deleted");
    }
}
