package com.proyectorestaurante.controller;

import com.proyectorestaurante.dto.CreateOrderRequest;
import com.proyectorestaurante.model.Order;
import com.proyectorestaurante.model.OrderStatus;
import com.proyectorestaurante.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @GetMapping
    public ResponseEntity<List<Order>> getUserOrders(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(orderService.getOrderById(userId, id));
    }
    
    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestBody CreateOrderRequest orderRequest,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(orderService.createOrder(userId, orderRequest));
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusUpdate) {
        OrderStatus status = OrderStatus.valueOf(statusUpdate.get("status"));
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
}
