package com.solvd.dp.repository;

import com.solvd.dp.domain.user.Order;
import com.solvd.dp.domain.user.OrderStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface OrderRepository {

    Optional<Order> findById(@Param("id") Long id);

    List<Order> getAllByUserId(@Param("userId") Long userId);

    List<Order> getAllByAddressId(@Param("addressId") Long addressId);

    List<Order> getAllByCourierId(@Param("courierId") Long courierId);

    List<Order> getAllByRestaurantId(@Param("restaurantId") Long restaurantId);

    List<Order> getAllByRestaurantIdAndStatus(@Param("restaurantId") Long restaurantId, @Param("status") OrderStatus status);

    void update(@Param("order") Order order);

    void create(@Param("order") Order order);

    boolean isOrderAssigned(@Param("id") Long id);

    void assignOrder(@Param("orderId") Long orderId, @Param("courierId") Long courierId);

    void updateStatus(@Param("id") Long id, @Param("status") OrderStatus status);

    void setDelivered(@Param("id") Long id);

    void addOrderById(@Param("orderId") Long orderId, @Param("userId") Long userId);

    void delete(@Param("id") Long id);

}
