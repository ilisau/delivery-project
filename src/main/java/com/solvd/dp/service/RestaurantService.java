package com.solvd.dp.service;

import com.solvd.dp.domain.restaurant.Restaurant;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> getAll();

    Restaurant getById(Long id);

    Restaurant getByName(String name);

    Restaurant save(Restaurant restaurantDto);

    Restaurant create(Restaurant restaurant);

    void addEmployeeById(Long id, Long employeeId);

    void deleteEmployeeById(Long id, Long employeeId);

    void addItemById(Long id, Long itemId);

    void deleteItemById(Long id, Long itemId);

    void addAddressById(Long id, Long addressId);

    void deleteAddressById(Long id, Long addressId);

    void deleteById(Long id);

}
