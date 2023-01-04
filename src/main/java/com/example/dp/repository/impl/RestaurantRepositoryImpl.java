package com.example.dp.repository.impl;

import com.example.dp.domain.restaurant.Restaurant;
import com.example.dp.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantRepositoryImpl implements RestaurantRepository {

    @Override
    public Optional<Restaurant> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Restaurant> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Restaurant save(Restaurant restaurantDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
