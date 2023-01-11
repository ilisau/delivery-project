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

    void addEmployeeById(Long id, Long employeeId);

    void deleteEmployeeById(Long id, Long employeeId);

    void addItemById(Long id, Long itemId);

    void deleteItemById(Long id, Long itemId);

    void addAddressById(Long id, Long addressId);

    void deleteAddressById(Long id, Long addressId);

    boolean isExists(Restaurant restaurant);

    boolean isEmployeeExists(Long id, Long employeeId);

    void delete(Long id);

}
