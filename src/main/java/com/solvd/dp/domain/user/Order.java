package com.solvd.dp.domain.user;

import com.solvd.dp.domain.courier.Courier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;
    private Address address;
    private Cart cart;
    private OrderStatus status;
    private Courier courier;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;

}
