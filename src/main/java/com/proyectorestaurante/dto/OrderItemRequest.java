package com.proyectorestaurante.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    private Long productId;
    private Long restaurantId;
    private Integer quantity;
    private Integer price;
    private String productName;
    private String productImage;
}
