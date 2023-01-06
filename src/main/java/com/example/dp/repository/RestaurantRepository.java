package com.example.dp.repository;

import com.example.dp.domain.restaurant.Restaurant;
import com.example.dp.web.dto.restaurant.CreateRestaurantDto;

import java.util.Optional;

public interface RestaurantRepository {

    Optional<Restaurant> findById(Long id);

    Optional<Restaurant> findByName(String name);

    Restaurant save(Restaurant restaurant);

    Restaurant create(CreateRestaurantDto dto);

    void addEmployeeById(Long id, Long employeeId);

    void deleteEmployeeById(Long id, Long employeeId);

    void addItemById(Long id, Long itemId);

    void deleteItemById(Long id, Long itemId);

    void addAddressById(Long id, Long addressId);

    void deleteAddressById(Long id, Long addressId);

    void delete(Long id);
}
