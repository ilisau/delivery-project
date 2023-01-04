package com.example.dp.domain.user;

import com.example.dp.domain.courier.Courier;
import com.example.dp.domain.restaurant.Restaurant;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class Order {

    private Long id;
    private User user;
    private Address address;
    private Restaurant restaurant;
    private Cart cart;
    private OrderStatus status;
    private Courier courier;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
