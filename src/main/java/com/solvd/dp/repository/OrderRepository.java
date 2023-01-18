package com.solvd.dp.repository;

import com.solvd.dp.domain.user.Order;
import com.solvd.dp.domain.user.OrderStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface OrderRepository {

    Optional<Order> findById(Long id);

    List<Order> getAllByUserId(Long userId);

    List<Order> getAllByAddressId(Long addressId);

    List<Order> getAllByCourierId(Long courierId);

    List<Order> getAllByRestaurantId(Long restaurantId);

    List<Order> getAllByRestaurantIdAndStatus(@Param("restaurantId") Long restaurantId, @Param("status") OrderStatus status);

    void update(Order order);

    void create(Order order);

    boolean isOrderAssigned(Long id);

    void assignOrder(@Param("orderId") Long orderId, @Param("courierId") Long courierId);

    void updateStatus(@Param("id") Long id, @Param("status") OrderStatus status);

    void setDelivered(Long id);

    void addOrderById(@Param("orderId") Long orderId, @Param("userId") Long userId);

    void delete(Long id);

}
