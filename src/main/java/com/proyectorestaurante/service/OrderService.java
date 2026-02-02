package com.proyectorestaurante.service;

import com.proyectorestaurante.dto.CreateOrderRequest;
import com.proyectorestaurante.dto.OrderItemRequest;
import com.proyectorestaurante.model.Order;
import com.proyectorestaurante.model.OrderItem;
import com.proyectorestaurante.model.OrderStatus;
import com.proyectorestaurante.repository.OrderItemRepository;
import com.proyectorestaurante.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Transactional
    public Order createOrder(Long userId, CreateOrderRequest request) {
        // Create order
        Order order = Order.builder()
                .userId(userId)
                .status(OrderStatus.PENDING)
                .subtotal(BigDecimal.valueOf(request.getSubtotal()))
                .deliveryFee(BigDecimal.valueOf(request.getDeliveryFee()))
                .total(BigDecimal.valueOf(request.getTotal()))
                .deliveryAddress(request.getDeliveryAddress())
                .notes(request.getNotes())
                .build();
        
        order = orderRepository.save(order);
        
        // Create order items
        for (OrderItemRequest itemRequest : request.getItems()) {
            OrderItem orderItem = OrderItem.builder()
                    .orderId(order.getId())
                    .productId(itemRequest.getProductId())
                    .restaurantId(itemRequest.getRestaurantId())
                    .quantity(itemRequest.getQuantity())
                    .price(BigDecimal.valueOf(itemRequest.getPrice()))
                    .productName(itemRequest.getProductName())
                    .productImage(itemRequest.getProductImage())
                    .build();
            
            orderItemRepository.save(orderItem);
        }
        
        return order;
    }
    
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    
    public Order getOrderById(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        // Verify order belongs to user
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to order");
        }
        
        return order;
    }
    
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
