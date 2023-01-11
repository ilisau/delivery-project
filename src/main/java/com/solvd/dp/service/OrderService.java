package com.solvd.dp.service;

import com.solvd.dp.domain.user.Order;
import com.solvd.dp.domain.user.OrderStatus;

import java.util.List;

public interface OrderService {

    Order getById(Long id);

    List<Order> getAllByUserId(Long id);

    List<Order> getAllByAddressId(Long id);

    List<Order> getAllByCourierId(Long id);

    Order save(Order order);

    Order create(Order order, Long userId);

    void changeStatus(Long id, OrderStatus status);

    boolean isOrderAssigned(Long orderId);

    void assignOrder(Long orderId, Long courierId);

    void updateStatus(Long orderId, OrderStatus status);

    void addOrderById(Long userId, Long orderId);

    void deleteOrderById(Long userId, Long orderId);

    void deleteById(Long id);

}
