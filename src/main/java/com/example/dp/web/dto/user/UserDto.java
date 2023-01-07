package com.example.dp.web.dto.user;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private List<AddressDto> addresses;
    private List<OrderDto> orders;
    private CartDto cart;
    private LocalDate createdAt;
}
