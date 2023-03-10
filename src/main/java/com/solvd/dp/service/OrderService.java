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

    Order update(Order order);

    Order create(Order order, Long userId);

    boolean isOrderAssigned(Long id);

    void assignOrder(Long orderId, Long courierId);

    void updateStatus(Long id, OrderStatus status);

    boolean isUserOwner(Long orderId, Long userId);

    boolean isCourierOwner(Long orderId, Long courierId);

    boolean isEmployeeOwner(Long orderId, Long employeeId);

    void delete(Long id);

}
