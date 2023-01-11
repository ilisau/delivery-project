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

    Order save(Order order);

    Order create(Order order);

    void changeStatus(Long id, OrderStatus status);

    boolean isOrderAssigned(Long id);

    void assignOrder(Long orderId, Long courierId);

    void updateStatus(Long id, OrderStatus status);

    void setDelivered(Long id);

    void addOrderById(Long userId, Long orderId);

    void deleteOrderById(Long userId, Long orderId);

    void delete(Long id);

}
