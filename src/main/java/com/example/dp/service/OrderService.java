package com.example.dp.service;

import com.example.dp.domain.user.Order;
import com.example.dp.web.dto.user.CreateOrderDto;

import java.util.List;

public interface OrderService {

    Order getById(Long id);

    List<Order> getAllByUserId(Long id);

    List<Order> getAllByAddressId(Long id);

    List<Order> getAllByCourierId(Long id);

    Order save(Order order);

    Order create(CreateOrderDto createOrderDto);

    void deleteById(Long id);
}
