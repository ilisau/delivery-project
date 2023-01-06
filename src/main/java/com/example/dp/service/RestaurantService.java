package com.example.dp.service;

import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.restaurant.Restaurant;
import com.example.dp.web.dto.restaurant.CreateRestaurantDto;
import com.example.dp.web.dto.restaurant.RestaurantDto;

public interface RestaurantService {

    Restaurant getById(Long id) throws ResourceNotFoundException;

    Restaurant getByName(String name) throws ResourceNotFoundException;

    Restaurant save(Restaurant restaurantDto);

    Restaurant create(CreateRestaurantDto createRestaurantDto);

    void addEmployeeById(Long id, Long employeeId);

    void deleteEmployeeById(Long id, Long employeeId);

    void addItemById(Long id, Long itemId);

    void deleteItemById(Long id, Long itemId);

    void addAddressById(Long id, Long addressId);

    void deleteAddressById(Long id, Long addressId);

    void deleteById(Long id);
}
