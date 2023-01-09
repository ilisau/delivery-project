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

    @PutMapping
    public void save(@Valid @RequestBody RestaurantDto dto) {
        Restaurant restaurant = RestaurantMapper.INSTANCE.toEntity(dto);
        restaurantService.save(restaurant);
    }

    @PostMapping
    public RestaurantDto create(@Valid @RequestBody CreateRestaurantDto createRestaurantDto) {
        Restaurant restaurant = restaurantService.create(createRestaurantDto);
        return RestaurantMapper.INSTANCE.toDto(restaurant);
    }

    @PostMapping("/{id}/add-employee/{employeeId}")
    public void addEmployeeById(@PathVariable Long id, @PathVariable Long employeeId) {
        restaurantService.addEmployeeById(id, employeeId);
    }

    @PostMapping("/{id}/delete-employee/{employeeId}")
    public void deleteEmployeeById(@PathVariable Long id, @PathVariable Long employeeId) {
        restaurantService.deleteEmployeeById(id, employeeId);
    }

    @PostMapping("/{id}/add-item/{itemId}")
    public void addItemById(@PathVariable Long id, @PathVariable Long itemId) {
        restaurantService.addItemById(id, itemId);
    }

    @PostMapping("/{id}/delete-item/{itemId}")
    public void deleteItemById(@PathVariable Long id, @PathVariable Long itemId) {
        restaurantService.deleteItemById(id, itemId);
    }

    @PostMapping("/{id}/add-address/{addressId}")
    public void addAddressById(@PathVariable Long id, @PathVariable Long addressId) {
        restaurantService.addAddressById(id, addressId);
    }

    @PostMapping("/{id}/delete-address/{addressId}")
    public void deleteAddressById(@PathVariable Long id, @PathVariable Long addressId) {
        restaurantService.deleteAddressById(id, addressId);
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
