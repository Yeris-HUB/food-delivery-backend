package com.proyectorestaurante.service;

import com.proyectorestaurante.model.Restaurant;
import com.proyectorestaurante.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    
    @Autowired
    private RestaurantRepository restaurantRepository;
    
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
    
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }
    
    public List<Restaurant> getRestaurantsByCategory(String category) {
        return restaurantRepository.findByCategory(category);
    }
    
    public List<Restaurant> searchRestaurants(String name) {
        return restaurantRepository.findByNameContainingIgnoreCase(name);
    }
}
