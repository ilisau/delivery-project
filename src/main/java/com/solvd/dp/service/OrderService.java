package com.solvd.dp.service;

import com.solvd.dp.domain.user.Order;
import com.solvd.dp.domain.user.OrderStatus;

import java.util.List;

public interface OrderService {

    Order getById(Long id);

    List<Order> getAllByUserId(Long userId);

    List<Order> getAllByAddressId(Long addressId);

    List<Order> getAllByCourierId(Long courierId);

    List<Order> getAllByRestaurantId(Long restaurantId);

    List<Order> getAllByRestaurantIdAndStatus(Long restaurantId, OrderStatus status);

    Order save(Order order);

    Order create(Order order, Long userId);

    boolean isOrderAssigned(Long id);

    void assignOrder(Long orderId, Long courierId);

    void updateStatus(Long id, OrderStatus status);

    void delete(Long id);

}
