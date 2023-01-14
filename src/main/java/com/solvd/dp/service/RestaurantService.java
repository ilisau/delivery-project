package com.solvd.dp.service;

import com.solvd.dp.domain.restaurant.Restaurant;
import com.solvd.dp.domain.user.Address;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> getAll();

    Restaurant getById(Long id);

    Restaurant getByName(String name);

    Restaurant update(Restaurant restaurantDto);

    Restaurant create(Restaurant restaurant);

    void addEmployeeById(Long restaurantId, Long employeeId);

    void deleteEmployeeById(Long restaurantId, Long employeeId);

    void addItemById(Long restaurantId, Long itemId);

    void deleteItemById(Long restaurantId, Long itemId);

    void addAddress(Long restaurantId, Address address);

    void deleteAddressById(Long restaurantId, Long addressId);

    void delete(Long id);

}
