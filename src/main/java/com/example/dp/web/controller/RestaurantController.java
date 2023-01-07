package com.example.dp.web.controller;

import com.example.dp.domain.restaurant.Restaurant;
import com.example.dp.service.RestaurantService;
import com.example.dp.web.dto.restaurant.CreateRestaurantDto;
import com.example.dp.web.dto.restaurant.RestaurantDto;
import com.example.dp.web.mapper.RestaurantMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/restaurants")
@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public RestaurantDto create(@Valid @RequestBody CreateRestaurantDto createRestaurantDto) {
        Restaurant restaurant = restaurantService.create(createRestaurantDto);
        return RestaurantMapper.INSTANCE.toDto(restaurant);
    }

    @GetMapping("/{id}")
    public RestaurantDto getById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getById(id);
        return RestaurantMapper.INSTANCE.toDto(restaurant);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        restaurantService.deleteById(id);
    }
}
