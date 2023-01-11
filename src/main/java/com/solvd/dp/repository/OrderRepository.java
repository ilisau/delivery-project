package com.solvd.dp.repository;

import com.solvd.dp.domain.user.Order;
import com.solvd.dp.domain.user.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(Long id);

    List<Order> getAllByUserId(Long userId);

    List<Order> getAllByAddressId(Long addressId);

    List<Order> getAllByCourierId(Long courierId);

    List<Order> getAllByRestaurantId(Long restaurantId);

    List<Order> getAllByRestaurantIdAndStatus(Long restaurantId, OrderStatus status);

    void save(Order order);

    void create(Order order);

    boolean isOrderAssigned(Long id);

    void assignOrder(Long orderId, Long courierId);

    void updateStatus(Long id, OrderStatus status);

    void setDelivered(Long id);

    void addOrderById(Long orderId, Long userId);

    void delete(Long id);

}
