package com.example.ecommerce.service;

import com.example.ecommerce.entity.Orders;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    ResponseEntity<String> placeOrder(Orders ordersRequest, String session) throws Exception;
    ResponseEntity<List<Orders>> getOrders(Long userId, String session) throws Exception;
    ResponseEntity<Orders> getOrder(Long id, String session) throws Exception;
    ResponseEntity<String> modifyOrder(Orders ordersRequest, String session) throws Exception;
    ResponseEntity<String> deleteOrder(Long id, String session) throws Exception;
}
