package com.proyectorestaurante.controller;

import com.proyectorestaurante.model.Product;
import com.proyectorestaurante.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Product>> getProductsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(productService.getProductsByRestaurant(restaurantId));
    }
    
    @GetMapping("/restaurant/{restaurantId}/available")
    public ResponseEntity<List<Product>> getAvailableProductsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(productService.getAvailableProductsByRestaurant(restaurantId));
    }
}
