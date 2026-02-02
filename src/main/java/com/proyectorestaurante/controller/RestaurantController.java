package com.proyectorestaurante.controller;

import com.proyectorestaurante.model.Restaurant;
import com.proyectorestaurante.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    
    @Autowired
    private RestaurantService restaurantService;
    
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(restaurantService.getRestaurantsByCategory(category));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurants(@RequestParam String name) {
        return ResponseEntity.ok(restaurantService.searchRestaurants(name));
    }
}
