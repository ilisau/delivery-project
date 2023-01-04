package com.example.dp.repository.impl;

import com.example.dp.domain.courier.Courier;
import com.example.dp.domain.restaurant.Restaurant;
import com.example.dp.domain.user.Address;
import com.example.dp.domain.user.Order;
import com.example.dp.domain.user.User;
import com.example.dp.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderRepositoryImpl implements OrderRepository {

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Order save(Order orderDto) {
        return null;
    }

    @Override
    public List<Order> getAllByUser(User user) {
        return null;
    }

    @Override
    public List<Order> getAllByAddress(Address address) {
        return null;
    }

    @Override
    public List<Order> getAlByRestaurant(Restaurant restaurant) {
        return null;
    }

    @Override
    public List<Order> getAllByCourier(Courier courier) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
