package com.example.dp.service;

import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.user.Order;
import com.example.dp.web.dto.user.CreateOrderDto;
import com.example.dp.web.dto.user.OrderDto;

import java.util.List;

public interface OrderService {

    Order getById(Long id) throws ResourceNotFoundException;

    List<Order> getAllByUserId(Long id) throws ResourceNotFoundException;

    List<Order> getAllByAddressId(Long id) throws ResourceNotFoundException;

    List<Order> getAllByRestaurantId(Long id) throws ResourceNotFoundException;

    List<Order> getAllByCourierId(Long id) throws ResourceNotFoundException;

    Order save(Order order);

    Order create(CreateOrderDto createOrderDto);

    void deleteById(Long id);
}
