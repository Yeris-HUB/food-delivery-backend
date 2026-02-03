package com.proyectorestaurante.service;

import com.proyectorestaurante.model.Product;
import com.proyectorestaurante.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
    
    public List<Product> getProductsByRestaurant(Long restaurantId) {
        return productRepository.findByRestaurantId(restaurantId);
    }
    
    public List<Product> getAvailableProductsByRestaurant(Long restaurantId) {
        return productRepository.findByRestaurantIdAndAvailableTrue(restaurantId);
    }
}
