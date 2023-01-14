package com.solvd.dp.domain.courier;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Courier {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime lastActiveAt;
    private CourierStatus status;
    private String phoneNumber;

}
