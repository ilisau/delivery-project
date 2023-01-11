package com.solvd.dp.repository;

import com.solvd.dp.domain.user.Order;
import com.solvd.dp.domain.user.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(Long id);

    List<Order> getAllByUserId(Long id);

    List<Order> getAllByAddressId(Long id);

    List<Order> getAllByCourierId(Long id);

    List<Order> getAllByRestaurantId(Long id);

    List<Order> getAllByRestaurantIdAndStatus(Long id, OrderStatus status);

    Order save(Order order);

    Order create(Order order);

    void changeStatus(Long id, OrderStatus status);

    void setCourierById(Long id, Long courierId);

    boolean isOrderAssigned(Long orderId);

    void assignOrder(Long orderId, Long courierId);

    void updateStatus(Long orderId, OrderStatus status);

    void setDelivered(Long id);

    void addOrderById(Long userId, Long orderId);

    void deleteOrderById(Long userId, Long orderId);

    void delete(Long id);

}
