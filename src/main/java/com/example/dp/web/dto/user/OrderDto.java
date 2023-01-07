package com.example.dp.web.dto.user;

import com.example.dp.domain.user.OrderStatus;
import com.example.dp.web.dto.courier.CourierDto;
import com.example.dp.web.dto.restaurant.RestaurantDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {

    private Long id;
    private AddressDto address;
    private RestaurantDto restaurant;
    private CartDto cart;
    private OrderStatus status;
    private CourierDto courier;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
}
