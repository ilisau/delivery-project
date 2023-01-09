package com.example.dp.repository;

import com.example.dp.domain.user.Order;
import com.example.dp.web.dto.user.CreateOrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(Long id);

    List<Order> getAllByUserId(Long id);

    List<Order> getAllByAddressId(Long id);

    List<Order> getAllByCourierId(Long id);

    Order save(Order order);

    Order create(CreateOrderDto createOrderDto);

    void delete(Long id);
}
