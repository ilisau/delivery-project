package com.solvd.dp.repository;

import com.solvd.dp.domain.restaurant.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {

    List<Restaurant> getAll();

    Optional<Restaurant> findById(Long id);

    Optional<Restaurant> findByName(String name);

    Restaurant save(Restaurant restaurant);

    Restaurant create(Restaurant restaurant);

    void addEmployeeById(Long restaurantId, Long employeeId);

    void deleteEmployeeById(Long restaurantId, Long employeeId);

    void addItemById(Long restaurantId, Long itemId);

    void deleteItemById(Long restaurantId, Long itemId);

    void addAddressById(Long restaurantId, Long addressId);

    void deleteAddressById(Long restaurantId, Long addressId);

    boolean employeeExists(Restaurant restaurant);

    boolean employeeExists(Long restaurantId, Long employeeId);

    void delete(Long id);

}
