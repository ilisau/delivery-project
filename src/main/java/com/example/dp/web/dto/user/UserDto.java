package com.example.dp.web.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private CartDto cart;
    private LocalDateTime createdAt;
}
