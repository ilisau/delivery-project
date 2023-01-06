package com.example.dp.repository;

import com.example.dp.domain.courier.Courier;
import com.example.dp.domain.restaurant.Restaurant;
import com.example.dp.domain.user.Address;
import com.example.dp.domain.user.Order;
import com.example.dp.domain.user.User;
import com.example.dp.web.dto.user.CreateOrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(Long id);

    List<Order> getAllByUserId(Long id);

    List<Order> getAllByAddressId(Long id);

    List<Order> getAllByRestaurantId(Long id);

    List<Order> getAllByCourierId(Long id);

    Order save(Order order);

    Order create(CreateOrderDto createOrderDto);

    void delete(Long id);
}
