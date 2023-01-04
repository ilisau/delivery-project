package com.example.dp.repository;

import com.example.dp.domain.courier.Courier;
import com.example.dp.domain.restaurant.Restaurant;
import com.example.dp.domain.user.Address;
import com.example.dp.domain.user.Order;
import com.example.dp.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(Long id);

    List<Order> getAllByUser(User user);

    List<Order> getAllByAddress(Address address);

    List<Order> getAlByRestaurant(Restaurant restaurant);

    List<Order> getAllByCourier(Courier courier);

    Order save(Order order);

    void delete(Long id);
}
