package com.example.dp.repository;

import com.example.dp.domain.restaurant.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {

    Optional<Restaurant> findById(Long id);

    Optional<Restaurant> findByName(String name);

    Restaurant save(Restaurant restaurant);

    void delete(Long id);
}
