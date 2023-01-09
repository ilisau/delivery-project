package com.example.dp.service;

import com.example.dp.domain.restaurant.Restaurant;
import com.example.dp.web.dto.restaurant.CreateRestaurantDto;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> getAll();

    Restaurant getById(Long id);

    Restaurant getByName(String name);

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
