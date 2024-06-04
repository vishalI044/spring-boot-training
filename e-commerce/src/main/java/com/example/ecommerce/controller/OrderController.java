package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Orders;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.implement.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/order")
public class OrderController {
    @Autowired
    OrderServiceImpl orderService;

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody Orders ordersRequest, @RequestHeader("session-id") String session) throws Exception {
        return orderService.placeOrder(ordersRequest, session);
    }

    @GetMapping("/get-all/{user_id}")
    public ResponseEntity<List<Orders>> getOrders(@PathVariable("user_id") Long userId,@RequestHeader("session-id") String session) throws Exception {
        return orderService.getOrders(userId, session);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Orders> getOrder(@PathVariable("id") Long id,@RequestHeader("session-id") String session) throws Exception {
        return orderService.getOrder(id, session);
    }

    @PutMapping("/modify")
    public ResponseEntity<String> modifyOrder(@RequestBody Orders ordersRequest,@RequestHeader("session-id") String session) throws Exception {
        return orderService.modifyOrder(ordersRequest, session);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") Long id,@RequestHeader("session-id") String session) throws Exception {
        return orderService.deleteOrder(id, session);
    }
}