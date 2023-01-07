package com.example.dp.domain.user;

import com.example.dp.domain.courier.Courier;
import com.example.dp.domain.restaurant.Restaurant;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {

    private Long id;
    private Address address;
    private Restaurant restaurant;
    private Cart cart;
    private OrderStatus status;
    private Courier courier;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
}
