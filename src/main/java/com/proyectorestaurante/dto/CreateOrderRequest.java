package com.proyectorestaurante.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    private List<OrderItemRequest> items;
    private Integer subtotal;
    private Integer deliveryFee;
    private Integer total;
    private String deliveryAddress;
    private String notes;
}
